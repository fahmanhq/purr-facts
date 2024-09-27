package app.purrfacts.feature.history

import androidx.annotation.StringRes
import app.purrfacts.data.api.model.Fact

sealed interface FactHistoryUiState {
    object Loading : FactHistoryUiState
    data class Success(val facts: List<Fact>) : FactHistoryUiState
    data class Error(@StringRes val errorMessageResId: Int) : FactHistoryUiState
}