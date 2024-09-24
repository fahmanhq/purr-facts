package app.purrfacts.feature.history

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.purrfacts.core.testing.android.onNodeWithTag
import app.purrfacts.core.ui.component.CommonComponentTestTags
import app.purrfacts.data.api.model.Fact
import com.google.common.truth.Truth
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FactHistoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val appContext: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun factHistoryIsDisplayed_whenHistoryIsNotEmpty() {
        val factHistory = listOf(
            Fact(1, "Fact 1"),
            Fact(2, "Fact 2"),
            Fact(3, "Fact 3")
        )
        val uiState = FactHistoryUiState.Success(factHistory)

        setUiStateToCompose(uiState) {}

        factHistory.forEach {
            composeTestRule.onNodeWithText(it.fact).assertIsDisplayed()
        }
    }

    @Test
    fun factHistoryEmptyPlaceholderIsDisplayed_whenHistoryIsEmpty() {
        val uiState = FactHistoryUiState.Success(emptyList<Fact>())

        setUiStateToCompose(uiState) {}

        composeTestRule.onNodeWithTag(FactHistoryScreenTestTags.EMPTY_HISTORY_PLACEHOLDER)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(
            appContext.getString(R.string.empty_history_placeholder_message)
        ).assertIsDisplayed()
    }

    @Test
    fun loadingIndicatorIsDisplayed_whenFactHistoryIsLoading() {
        val uiState = FactHistoryUiState.Loading

        setUiStateToCompose(uiState) {}

        composeTestRule.onNodeWithTag(CommonComponentTestTags.LOADING_INDICATOR)
            .assertIsDisplayed()
    }

    @Test
    fun loadingIndicatorIsNotDisplayed_whenFactHistoryIsNotLoading() {
        val uiState = FactHistoryUiState.Success(emptyList<Fact>())

        setUiStateToCompose(uiState) {}

        composeTestRule.onNodeWithTag(CommonComponentTestTags.LOADING_INDICATOR)
            .assertIsNotDisplayed()
    }

    @Test
    fun errorIndicatorIsDisplayed_whenFactHistoryIsError() {
        val sampleErrorMessageResId = app.purrfacts.core.ui.R.string.error_msg_unknown_issue
        val uiState = FactHistoryUiState.Error(sampleErrorMessageResId)

        setUiStateToCompose(uiState) {}

        composeTestRule.onNodeWithTag(CommonComponentTestTags.ERROR_INDICATOR)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(appContext.getString(sampleErrorMessageResId))
            .assertIsDisplayed()
    }

    @Test
    fun errorIndicatorIsNotDisplayed_whenFactHistoryIsNotError() {
        val uiState = FactHistoryUiState.Success(emptyList<Fact>())

        setUiStateToCompose(uiState) {}

        composeTestRule.onNodeWithTag(CommonComponentTestTags.ERROR_INDICATOR)
            .assertIsNotDisplayed()
    }

    @Test
    fun onRetryButtonClickedCallbackIsCalled_whenRetryBtnClicked() {
        var callbackCalled = false

        val sampleErrorMessageResId = app.purrfacts.core.ui.R.string.error_msg_unknown_issue
        setUiStateToCompose(
            FactHistoryUiState.Error(sampleErrorMessageResId),
            onRetryButtonClicked = {
                callbackCalled = true
            }
        )

        composeTestRule.onNodeWithTag(CommonComponentTestTags.ERROR_INDICATOR)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(appContext.getString(app.purrfacts.core.ui.R.string.retry))
            .performClick()

        Truth.assertThat(callbackCalled).isTrue()
    }

    private fun setUiStateToCompose(uiState: FactHistoryUiState, onRetryButtonClicked: () -> Unit) {
        composeTestRule.setContent {
            FactHistoryScreen(
                uiState = uiState,
                onRetryButtonClicked = onRetryButtonClicked
            )
        }
    }
}
