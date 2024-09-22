package app.purrfacts.feature.factforyou

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object FactForYou

fun NavGraphBuilder.factForYouScreen() {
    composable<FactForYou> {
        FactScreen()
    }
}