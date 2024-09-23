package app.purrfacts.feature.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.purrfacts.core.ui.Result
import app.purrfacts.data.api.model.Fact
import app.purrfacts.data.api.repository.FactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactHistoryViewModel @Inject constructor(
    private val factRepository: FactRepository
) : ViewModel() {

    var uiState by mutableStateOf<Result<List<Fact>>>(Result.Loading)
        private set

    init {
        loadStartingFact()
    }

    private fun loadStartingFact() {
        viewModelScope.launch {
            runCatching {
                uiState = Result.Loading
                uiState = Result.Success(
                    factRepository.getAllSavedFacts()
                )
            }.onFailure {
                uiState = Result.Error(it)
                it.printStackTrace()
            }
        }
    }
}