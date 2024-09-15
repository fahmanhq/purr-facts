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

                val newFact = factRepository.getLastSavedFact()
                uiState = Result.Success(
                    FactUiState(
                        fact = newFact.fact,
                        isMultipleCatsFact = newFact.fact.contains("cats", ignoreCase = true),
                        length = newFact.length
                    )
                )
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
                uiState = Result.Success(
                    FactUiState(
                        fact = newFact.fact,
                        isMultipleCatsFact = newFact.fact.contains("cats", ignoreCase = true),
                        length = newFact.length
                    )
                )
            }.onFailure {
                uiState = Result.Error(it)
                it.printStackTrace()
            }
        }
    }

    data class FactUiState(
        val fact: String,
        val isMultipleCatsFact: Boolean,
        val length: Int
    )
}


