package app.purrfacts.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import app.purrfacts.core.ui.ColorPalette
import app.purrfacts.core.ui.AppTheme

@Composable
internal fun ErrorIndicator(
    modifier: Modifier,
    customMessage: String? = null,
    retryAllowed: Boolean,
    onRetryButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .testTag("ErrorIndicator"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            tint = ColorPalette.RedMain,
            imageVector = Icons.Default.Info, contentDescription = null
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Error Occurred",
            style = MaterialTheme.typography.titleLarge,
            color = ColorPalette.RedMain
        )

        if (customMessage != null) {
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = customMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = ColorPalette.RedSecondary,
                textAlign = TextAlign.Center
            )
        }

        if (retryAllowed) {
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                content = {
                    Text(
                        text = "Retry",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                border = BorderStroke(
                    width = 1.dp,
                    color = ColorPalette.RedSecondary
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = ColorPalette.RedMain
                ),
                onClick = onRetryButtonClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorIndicatorPreview() {
    AppTheme {
        ErrorIndicator(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x88FFFFFF)),
            customMessage = LoremIpsum(10).values.first(),
            retryAllowed = true
        ) {}
    }
}