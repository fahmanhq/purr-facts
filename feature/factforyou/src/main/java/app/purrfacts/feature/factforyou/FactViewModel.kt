package app.purrfacts.feature.factforyou

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.purrfacts.core.ui.Result
import app.purrfacts.data.api.FactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LONG_FACT_THRESHOLD = 100
private const val CATS_KEYWORD = "cats"

@HiltViewModel
class FactViewModel @Inject constructor(
    private val factRepository: FactRepository
) : ViewModel() {

    var uiState by mutableStateOf<Result<FactUiState>>(Result.Loading)
        private set

    init {
        loadStartingFact()
    }

    private fun loadStartingFact() {
        viewModelScope.launch {
            runCatching {
                uiState = Result.Loading

                val lastSavedFact = factRepository.getLastSavedFact()
                uiState = lastSavedFact.fact.let {
                    Result.Success(
                        FactUiState(
                            fact = it,
                            containsCats = it.contains(CATS_KEYWORD, ignoreCase = true),
                            isLongFact = it.length > LONG_FACT_THRESHOLD
                        )
                    )
                }
            }.onFailure {
                uiState = Result.Error(it)
                it.printStackTrace()
            }
        }
    }

    fun updateFact() {
        viewModelScope.launch {
            runCatching {
                uiState = Result.Loading

                val newFact = factRepository.getNewFact()
                uiState = newFact.fact.let {
                    Result.Success(
                        FactUiState(
                            fact = it,
                            containsCats = it.contains(CATS_KEYWORD, ignoreCase = true),
                            isLongFact = it.length > LONG_FACT_THRESHOLD
                        )
                    )
                }
            }.onFailure {
                uiState = Result.Error(it)
                it.printStackTrace()
            }
        }
    }

    data class FactUiState(
        val fact: String,
        val containsCats: Boolean,
        val isLongFact: Boolean
    )
}


