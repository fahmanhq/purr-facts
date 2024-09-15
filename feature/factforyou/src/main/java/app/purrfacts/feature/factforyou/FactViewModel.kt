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
        updateFact()
    }

    fun updateFact() {
        viewModelScope.launch {
            runCatching {
                uiState = Result.Loading

                val newFact = factRepository.getNewFact()
                uiState = Result.Success(
                    FactUiState(
                        fact = newFact,
                        isMultipleCatsFact = newFact.contains("cats", ignoreCase = true),
                        length = newFact.length
                    )
                )
            }.onFailure {
                uiState = Result.Error(it)
            }
        }
    }

    data class FactUiState(
        val fact: String,
        val isMultipleCatsFact: Boolean,
        val length: Int
    )
}


