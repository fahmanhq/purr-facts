package jp.speakbuddy.edisonandroidexercise

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.purrfacts.core.ui.AppTheme
import app.purrfacts.feature.factforyou.FactForYou
import app.purrfacts.feature.history.FactHistory
import kotlinx.coroutines.launch

@Composable
internal fun HomePageScreen(
    topLevelRoutes: List<TopLevelRoute<*>> = TOP_LEVEL_ROUTES,
) {
    AppTheme {
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState(pageCount = { topLevelRoutes.size })
        val activeTopLevelRoute by remember {
            derivedStateOf { topLevelRoutes[pagerState.currentPage] }
        }

        Scaffold(
            bottomBar = {
                NavigationBar() {
                    topLevelRoutes.forEachIndexed { index, topLevelRoute ->
                        NavigationBarItem(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = topLevelRoute.icon,
                                    contentDescription = topLevelRoute.route::class.simpleName
                                )
                            }
                        )
                    }
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    )
            ) {
                HorizontalPager(state = pagerState) { pageIdx ->
                    topLevelRoutes[pageIdx].screen(activeTopLevelRoute)
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomePageScreenPreview() {
    @Composable
    fun ScreenSample(text: String) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text
            )
        }
    }

    HomePageScreen(
        listOf(
            TopLevelRoute(
                route = FactForYou,
                icon = Icons.Default.Home,
                screen = {
                    ScreenSample(
                        text = "FactForYou Route Screen"
                    )
                }
            ),
            TopLevelRoute(
                route = FactHistory,
                icon = Icons.Default.DateRange,
                screen = {
                    ScreenSample(
                        text = "FactHistory Route Screen"
                    )
                }
            )
        )
    )
}