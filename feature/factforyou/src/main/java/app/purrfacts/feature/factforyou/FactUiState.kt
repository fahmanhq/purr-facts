package app.purrfacts.feature.factforyou

import androidx.annotation.StringRes

sealed interface FactUiState {
    object Loading : FactUiState
    data class Success(val factSpec: FactSpec) : FactUiState
    data class Error(@StringRes val errorMessageResId: Int) : FactUiState
}

data class FactSpec(
    val fact: String,
    val containsCats: Boolean,
    val isLongFact: Boolean
)