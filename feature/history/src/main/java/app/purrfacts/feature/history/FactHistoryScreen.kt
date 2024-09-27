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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.purrfacts.core.ui.AppTheme
import app.purrfacts.core.ui.component.ErrorIndicator
import app.purrfacts.core.ui.component.LoadingIndicator
import app.purrfacts.core.ui.ext.testTag
import app.purrfacts.data.api.model.Fact

enum class FactHistoryScreenTestTags {
    EMPTY_HISTORY_PLACEHOLDER
}

@Composable
fun FactHistoryScreen(
    viewModel: FactHistoryViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadFactHistory()
    }

    FactHistoryScreen(
        uiState = viewModel.uiState,
        onRetryButtonClicked = viewModel::onRetryButtonClicked
    )
}

@Composable
internal fun FactHistoryScreen(uiState: FactHistoryUiState, onRetryButtonClicked: () -> Unit) {
    when (uiState) {
        FactHistoryUiState.Loading -> LoadingIndicator()
        is FactHistoryUiState.Success -> FactHistoryScreenContent(uiState.facts)
        is FactHistoryUiState.Error -> ErrorIndicator(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x88FFFFFF)),
            customMessage = stringResource(id = uiState.errorMessageResId),
            retryAllowed = true,
            onRetryButtonClicked = onRetryButtonClicked
        )
    }
}

@Composable
private fun FactHistoryScreenContent(data: List<Fact>) {
    if (data.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .testTag(FactHistoryScreenTestTags.EMPTY_HISTORY_PLACEHOLDER),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
            Text(
                text = stringResource(R.string.empty_history_placeholder_message),
                style = AppTheme.typography.titleMedium
            )
        }
    } else {
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
            uiState = FactHistoryUiState.Success(
                listOf(
                    Fact(id = 8028, fact = "neglegentur"),
                    Fact(id = 5179, fact = "appetere")
                )
            ),
            onRetryButtonClicked = {}
        )
    }
}

@Preview
@Composable
private fun FactHistoryScreenEmptyStatePreview() {
    AppTheme {
        FactHistoryScreen(
            uiState = FactHistoryUiState.Success(
                emptyList()
            ),
            onRetryButtonClicked = {}
        )
    }
}