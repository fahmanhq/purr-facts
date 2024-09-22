package jp.speakbuddy.edisonandroidexercise

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.purrfacts.feature.factforyou.FactForYou
import app.purrfacts.feature.factforyou.factForYouScreen
import kotlinx.serialization.Serializable

@Serializable
data object Home

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = FactForYou) {
        factForYouScreen()
    }
}