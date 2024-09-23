package app.purrfacts.feature.factforyou

sealed interface FactUiState {
    object Loading : FactUiState
    data class Success(val factSpec: FactSpec) : FactUiState
    data class Error(val throwable: Throwable) : FactUiState
}

data class FactSpec(
    val fact: String,
    val containsCats: Boolean,
    val isLongFact: Boolean
)