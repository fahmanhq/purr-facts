package app.purrfacts.feature.factforyou

import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.purrfacts.core.logger.AppLogger
import app.purrfacts.data.api.repository.FactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import app.purrfacts.core.ui.R as CoreUiR

private const val LONG_FACT_THRESHOLD = 100
private const val CATS_KEYWORD = "cats"

@HiltViewModel
class FactViewModel @Inject constructor(
    private val factRepository: FactRepository,
    private val appLogger: AppLogger
) : ViewModel() {

    var isInit = true
        @VisibleForTesting set

    var uiState by mutableStateOf<FactUiState>(FactUiState.Loading)
        @VisibleForTesting set

    private var thingToRetry: (() -> Unit)? = null

    fun loadStartingFact() {
        if (isInit) {
            viewModelScope.launch {
                runCatching {
                    uiState = FactUiState.Loading

                    val lastSavedFact = factRepository.getLastSavedFact()
                    uiState = lastSavedFact.fact.let {
                        FactUiState.Success(
                            createFactSpec(it)
                        )
                    }
                    isInit = false
                }.onFailure {
                    uiState = FactUiState.Error(getReadableErrorMessage(it))
                    thingToRetry = ::loadStartingFact
                    appLogger.logError(it, "Error loading starting fact")
                }
            }
        }
    }

    fun updateFact() {
        viewModelScope.launch {
            runCatching {
                uiState = FactUiState.Loading

                val newFact = factRepository.getNewFact()
                uiState = newFact.fact.let {
                    FactUiState.Success(
                        createFactSpec(it)
                    )
                }
            }.onFailure {
                uiState = FactUiState.Error(getReadableErrorMessage(it))
                thingToRetry = ::updateFact
                appLogger.logError(it, "Error updating fact")
            }
        }
    }

    @StringRes
    private fun getReadableErrorMessage(throwable: Throwable): Int = when (throwable) {
        is IOException -> CoreUiR.string.error_msg_network_issue
        else -> CoreUiR.string.error_msg_unknown_issue
    }

    @VisibleForTesting
    internal fun createFactSpec(fact: String) = FactSpec(
        fact = fact,
        containsCats = fact.contains(CATS_KEYWORD, ignoreCase = true),
        isLongFact = fact.length > LONG_FACT_THRESHOLD
    )

    fun onRetryButtonClicked() {
        thingToRetry?.invoke()
        thingToRetry = null
    }
}
