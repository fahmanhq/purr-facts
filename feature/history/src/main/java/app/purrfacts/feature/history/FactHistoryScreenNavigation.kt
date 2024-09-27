package app.purrfacts.feature.history

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object FactHistory

fun NavGraphBuilder.factHistoryScreen() {
    composable<FactHistory> {
        FactHistoryScreen()
    }
}