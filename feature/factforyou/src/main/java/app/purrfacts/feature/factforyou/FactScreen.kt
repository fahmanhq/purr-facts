package app.purrfacts.feature.factforyou

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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import app.purrfacts.core.ui.AppTheme
import app.purrfacts.core.ui.Result
import app.purrfacts.core.ui.component.LoadingIndicator

@Composable
fun FactScreen(
    viewModel: FactViewModel
) {
    FactScreen(
        factUiState = viewModel.uiState,
        onUpdateFactBtnClicked = {
            viewModel.updateFact()
        }
    )
}

@Composable
private fun FactScreen(
    factUiState: Result<FactViewModel.FactUiState>,
    onUpdateFactBtnClicked: () -> Unit,
) {
    when (factUiState) {
        Result.Loading -> LoadingIndicator()
        is Result.Success -> FactScreenContent(factUiState, onUpdateFactBtnClicked)
        is Result.Error -> TODO()
    }
}

@Composable
fun FactScreenContent(
    factUiState: Result.Success<FactViewModel.FactUiState>,
    onUpdateFactBtnClicked: () -> Unit
) {
    val isMultipleCatsFactNoteVisible = factUiState.data.isMultipleCatsFact
    val displayedFact = factUiState.data.fact
    val lengthOfFact = factUiState.data.length
    val isLengthDisplayed by remember {
        derivedStateOf { lengthOfFact > 100 }
    }

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
                text = "Fact",
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
                        .padding(8.dp),
                    text = "Multiple Cats!",
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

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedVisibility(visible = isLengthDisplayed) {
            Text(
                text = "Length: $lengthOfFact",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = onUpdateFactBtnClicked
        ) {
            Text(text = "Update fact")
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
                    isMultipleCatsFact = true,
                    length = 101,
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
