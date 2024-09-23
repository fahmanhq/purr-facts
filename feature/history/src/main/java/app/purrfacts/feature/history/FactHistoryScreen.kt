package app.purrfacts.feature.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.purrfacts.core.ui.AppTheme
import app.purrfacts.core.ui.Result
import app.purrfacts.core.ui.component.ErrorIndicator
import app.purrfacts.core.ui.component.LoadingIndicator
import app.purrfacts.data.api.model.Fact


@Composable
internal fun FactHistoryScreen(
    viewModel: FactHistoryViewModel = hiltViewModel()
) {
    FactHistoryScreen(
        uiState = viewModel.uiState
    )
}

@Composable
private fun FactHistoryScreen(uiState: Result<List<Fact>>) {
    when (uiState) {
        Result.Loading -> LoadingIndicator()
        is Result.Success -> FactHistoryScreenContent(uiState.data)
        is Result.Error -> ErrorIndicator(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x88FFFFFF)),
            customMessage = uiState.exception.message,
            retryAllowed = true
        ) {}
    }
}

@Composable
private fun FactHistoryScreenContent(data: List<Fact>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(data, key = { it.id }) {
            FactHistoryItem(fact = it)
        }
    }
}

@Composable
private fun FactHistoryItem(fact: Fact) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(text = fact.fact, style = AppTheme.typography.titleMedium)
            Text(text = "Length: ${fact.fact.length}", style = AppTheme.typography.bodySmall)
        }
    }
}

@Preview
@Composable
private fun FactHistoryScreenPreview() {
    AppTheme {
        FactHistoryScreen(
            uiState = Result.Success(
                listOf(
                    Fact(id = 8028, fact = "neglegentur"),
                    Fact(id = 5179, fact = "appetere")
                )
            )
        )
    }
}