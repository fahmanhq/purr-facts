package jp.speakbuddy.edisonandroidexercise

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.purrfacts.feature.factforyou.FactForYou
import app.purrfacts.feature.factforyou.FactScreen
import app.purrfacts.feature.factforyou.factForYouScreen
import app.purrfacts.feature.history.FactHistory
import app.purrfacts.feature.history.FactHistoryScreen
import app.purrfacts.feature.history.factHistoryScreen

data class TopLevelRoute<T : Any>(
    val route: T,
    val icon: ImageVector,
    val screen: @Composable (TopLevelRoute<*>) -> Unit
)

val TOP_LEVEL_ROUTES = listOf(
    TopLevelRoute(
        route = FactForYou,
        icon = Icons.Default.Home,
        screen = { FactScreen() }
    ),
    TopLevelRoute(
        route = FactHistory,
        icon = Icons.Default.DateRange,
        screen = {
            FactHistoryScreen(
                isBackToActive = it.route == FactHistory,
            )
        }
    ),
)

/**
 *  Used if only we prefer nav host over Pager on Home Page
 */
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = FactForYou) {
        factForYouScreen()
        factHistoryScreen()
    }
}