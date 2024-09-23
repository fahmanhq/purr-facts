package app.purrfacts.feature.factforyou

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.purrfacts.data.api.repository.FactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LONG_FACT_THRESHOLD = 100
private const val CATS_KEYWORD = "cats"

@HiltViewModel
class FactViewModel @Inject constructor(
    private val factRepository: FactRepository
) : ViewModel() {

    var isInit = true
        @VisibleForTesting set

    var uiState by mutableStateOf<FactUiState>(FactUiState.Loading)
        @VisibleForTesting set

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
                    uiState = FactUiState.Error(it)
                    it.printStackTrace()
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
                uiState = FactUiState.Error(it)
                it.printStackTrace()
            }
        }
    }

    @VisibleForTesting
    internal fun createFactSpec(fact: String) = FactSpec(
        fact = fact,
        containsCats = fact.contains(CATS_KEYWORD, ignoreCase = true),
        isLongFact = fact.length > LONG_FACT_THRESHOLD
    )
}
