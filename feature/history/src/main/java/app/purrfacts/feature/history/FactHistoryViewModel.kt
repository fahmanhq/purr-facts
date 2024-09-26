package app.purrfacts.feature.history

import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.purrfacts.core.logger.AppLogger
import app.purrfacts.core.ui.R
import app.purrfacts.data.api.repository.FactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactHistoryViewModel @Inject constructor(
    private val factRepository: FactRepository,
    private val appLogger: AppLogger
) : ViewModel() {

    var isInit = true
        @VisibleForTesting set

    var uiState by mutableStateOf<FactHistoryUiState>(FactHistoryUiState.Loading)
        @VisibleForTesting set

    private var thingToRetry: (() -> Unit)? = null

    fun loadFactHistory() {
        if (isInit) {
            viewModelScope.launch {
                runCatching {
                    uiState = FactHistoryUiState.Loading
                    uiState = FactHistoryUiState.Success(
                        factRepository.getAllSavedFacts()
                    )
                    isInit = false
                }.onFailure {
                    uiState = FactHistoryUiState.Error(getReadableErrorMessage(it))
                    thingToRetry = ::loadFactHistory
                    appLogger.logError(it, "Error loading fact history")
                }
            }
        }
    }

    @StringRes
    private fun getReadableErrorMessage(throwable: Throwable): Int = when (throwable) {
        else -> R.string.error_msg_unknown_issue
    }

    fun onRetryButtonClicked() {
        thingToRetry?.invoke()
        thingToRetry = null
    }
}

