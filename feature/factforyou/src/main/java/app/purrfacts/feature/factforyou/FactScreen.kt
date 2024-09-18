package app.purrfacts.feature.factforyou

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.purrfacts.core.ui.AppTheme
import app.purrfacts.core.ui.Result
import app.purrfacts.core.ui.component.ErrorIndicator
import app.purrfacts.core.ui.component.LoadingIndicator
import app.purrfacts.core.ui.ext.testTag

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
internal enum class FactScreenTestTags {
    MULTIPLE_CATS_INDICATOR,
    FACT_LENGTH_INDICATOR
}

@Composable
internal fun FactScreen(
    viewModel: FactViewModel = hiltViewModel()
) {
    FactScreen(
        factUiState = viewModel.uiState,
        onUpdateFactBtnClicked = {
            viewModel.updateFact()
        }
    )
}

@Composable
internal fun FactScreen(
    factUiState: Result<FactViewModel.FactUiState>,
    onUpdateFactBtnClicked: () -> Unit,
) {
    when (factUiState) {
        Result.Loading -> LoadingIndicator()
        is Result.Success -> FactScreenContent(factUiState.data, onUpdateFactBtnClicked)
        is Result.Error -> ErrorIndicator(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x88FFFFFF)),
            customMessage = factUiState.exception.message,
            retryAllowed = true
        ) {}
    }
}

@Composable
private fun FactScreenContent(
    factUiState: FactViewModel.FactUiState,
    onUpdateFactBtnClicked: () -> Unit
) {
    val isMultipleCatsFactNoteVisible = factUiState.containsCats
    val displayedFact = factUiState.fact

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.fact_for_you_title),
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(
                visible = isMultipleCatsFactNoteVisible,
                enter = fadeIn() + expandHorizontally() + scaleIn()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .graphicsLayer(rotationZ = 10f)
                        .background(Color.Red, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .testTag(FactScreenTestTags.MULTIPLE_CATS_INDICATOR),
                    text = stringResource(R.string.multiple_cats_indicator_label),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = displayedFact,
            style = MaterialTheme.typography.bodyLarge
        )

        AnimatedVisibility(visible = factUiState.isLongFact) {
            Text(
                text = stringResource(R.string.length_indicator_template, factUiState.fact.length),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
                    .testTag(FactScreenTestTags.FACT_LENGTH_INDICATOR)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .semantics {

                },
            onClick = onUpdateFactBtnClicked
        ) {
            Text(text = stringResource(R.string.update_fact_btn_label))
        }
    }
}

@Preview
@Composable
private fun FactScreenPreview() {
    AppTheme {
        FactScreen(
            factUiState = Result.Success(
                FactViewModel.FactUiState(
                    fact = "This is a fact for multiple cats.\n${LoremIpsum(10).values.first()}",
                    containsCats = true,
                    isLongFact = true,
                )
            ),
            onUpdateFactBtnClicked = {}
        )
    }
}

@Preview
@Composable
private fun FactScreenOnLoadingStatePreview() {
    AppTheme {
        FactScreen(
            factUiState = Result.Loading,
            onUpdateFactBtnClicked = {}
        )
    }
}
