package jp.speakbuddy.edisonandroidexercise

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.filters.LargeTest
import app.purrfacts.core.testing.android.hasTestTag
import app.purrfacts.core.testing.android.onNodeWithTag
import app.purrfacts.core.ui.component.CommonComponentTestTags
import app.purrfacts.feature.factforyou.FactScreenTestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
@LargeTest
@HiltAndroidTest
class FactScreenE2ETest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun onActivityRecreate_factTextIsRedisplayed() {
        composeTestRule.waitUntilDoesNotExist(
            hasTestTag(CommonComponentTestTags.LOADING_INDICATOR),
            timeoutMillis = 5000
        )
        composeTestRule.onNodeWithTag(FactScreenTestTags.FACT_TEXT)
            .assertIsDisplayed()

        composeTestRule.activityRule.scenario.recreate()

        composeTestRule.waitUntilDoesNotExist(
            hasTestTag(CommonComponentTestTags.LOADING_INDICATOR)
        )
        composeTestRule.onNodeWithTag(FactScreenTestTags.FACT_TEXT)
            .assertIsDisplayed()
    }
}