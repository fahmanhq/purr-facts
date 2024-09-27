package app.purrfacts.feature.factforyou

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.purrfacts.core.testing.android.GrantPostNotificationsPermissionRule
import app.purrfacts.core.testing.android.onNodeWithTag
import app.purrfacts.core.ui.component.CommonComponentTestTags
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import app.purrfacts.core.ui.R as CoreUiR

private const val LONG_FACT_THRESHOLD = 100

@RunWith(AndroidJUnit4::class)
class FactScreenTest {

    @get:Rule(order = 0)
    val permissionRule = GrantPostNotificationsPermissionRule()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private val appContext: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun catFactIsDisplayed() {
        val fact = "Cats are curious animals and they love to explore."
        setupContentToTest(fact)

        composeTestRule
            .onNodeWithText(fact)
            .assertIsDisplayed()
    }

    @Test
    fun multipleCatsFactIndicatorIsDisplayed_whenFactIsMultipleCatsFact() {
        val fact = "Cats are amazing pets, and many people love them."
        setupContentToTest(fact)

        composeTestRule
            .onNodeWithTag(FactScreenTestTags.MULTIPLE_CATS_INDICATOR)
            .assertIsDisplayed()
    }

    @Test
    fun multipleCatsFactIndicatorIsNotDisplayed_whenFactIsNotMultipleCatsFact() {
        val fact = "Felines are known for their independence and agility."
        setupContentToTest(fact)

        composeTestRule
            .onNodeWithTag(FactScreenTestTags.MULTIPLE_CATS_INDICATOR)
            .assertIsNotDisplayed()
    }

    @Test
    fun multipleCatsFactIndicatorIsDisplayed_whenFactIsALongFact() {
        val longFact =
            "Cats are often valued by humans for companionship and their ability to hunt vermin. " +
                    "They are skilled predators known for their stealth and agility."
        val expectedLengthText =
            appContext.getString(R.string.length_indicator_template, longFact.length)

        setupContentToTest(longFact)

        composeTestRule
            .onNodeWithTag(FactScreenTestTags.FACT_LENGTH_INDICATOR)
            .assertIsDisplayed()
            .assertTextEquals(expectedLengthText)
    }

    @Test
    fun multipleCatsFactIndicatorIsNotDisplayed_whenFactIsAShortFact() {
        val shortFact = "Cats are curious creatures."

        setupContentToTest(shortFact)

        composeTestRule
            .onNodeWithTag(FactScreenTestTags.FACT_LENGTH_INDICATOR)
            .assertIsNotDisplayed()
    }

    @Test
    fun loadingIndicatorIsDisplayed_whenFactIsLoading() {
        composeTestRule.setContent {
            FactScreen(
                factUiState = FactUiState.Loading,
                onUpdateFactBtnClicked = {},
                onRetryButtonClicked = {}
            )
        }

        composeTestRule.onNodeWithTag(CommonComponentTestTags.LOADING_INDICATOR)
            .assertIsDisplayed()
    }

    @Test
    fun loadingIndicatorIsHidden_whenFactIsNotLoading() {
        setupContentToTest("Some Fact")

        composeTestRule.onNodeWithTag(CommonComponentTestTags.LOADING_INDICATOR)
            .assertIsNotDisplayed()
    }

    @Test
    fun errorIndicatorIsDisplayed_whenFactIsError() {
        val sampleErrorMessageResId = CoreUiR.string.error_msg_unknown_issue
        composeTestRule.setContent {
            FactScreen(
                factUiState = FactUiState.Error(sampleErrorMessageResId),
                onUpdateFactBtnClicked = {},
                onRetryButtonClicked = {}
            )
        }

        composeTestRule.onNodeWithTag(CommonComponentTestTags.ERROR_INDICATOR)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(appContext.getString(sampleErrorMessageResId))
            .assertIsDisplayed()
    }

    @Test
    fun errorIndicatorIsHidden_whenFactIsNotError() {
        setupContentToTest("Some Fact")

        composeTestRule.onNodeWithTag(CommonComponentTestTags.ERROR_INDICATOR)
            .assertIsNotDisplayed()
    }

    @Test
    fun onUpdateFactBtnClickedCallbackIsCalled_whenUpdateFactBtnClicked() {
        var callbackCalled = false

        setupContentToTest("Some Fact") {
            callbackCalled = true
        }

        composeTestRule
            .onNodeWithContentDescription(
                appContext.getString(R.string.update_fact_btn_label)
            )
            .performClick()

        assertThat(callbackCalled).isTrue()
    }

    @Test
    fun onRetryButtonClickedCallbackIsCalled_whenRetryBtnClicked() {
        var callbackCalled = false

        val sampleErrorMessageResId = CoreUiR.string.error_msg_unknown_issue
        composeTestRule.setContent {
            FactScreen(
                factUiState = FactUiState.Error(sampleErrorMessageResId),
                onUpdateFactBtnClicked = {},
                onRetryButtonClicked = { callbackCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(CommonComponentTestTags.ERROR_INDICATOR)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(appContext.getString(CoreUiR.string.retry))
            .performClick()

        assertThat(callbackCalled).isTrue()
    }

    private fun setupContentToTest(fact: String, onUpdateFactBtnClicked: () -> Unit = {}) {
        composeTestRule.setContent {
            FactScreen(
                factUiState = FactUiState.Success(
                    FactSpec(
                        fact = fact,
                        containsCats = fact.contains("cats", ignoreCase = true),
                        isLongFact = fact.length.toLong() > LONG_FACT_THRESHOLD
                    )
                ),
                onUpdateFactBtnClicked = onUpdateFactBtnClicked,
                onRetryButtonClicked = {}
            )
        }
    }
}