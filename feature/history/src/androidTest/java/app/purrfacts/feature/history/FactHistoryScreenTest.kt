package app.purrfacts.feature.history

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import app.purrfacts.core.testing.android.onNodeWithTag
import app.purrfacts.core.ui.Result
import app.purrfacts.core.ui.component.CommonComponentTestTags
import app.purrfacts.data.api.model.Fact
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
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
        val uiState = Result.Success(factHistory)

        setUiStateToCompose(uiState)

        factHistory.forEach {
            composeTestRule.onNodeWithText(it.fact).assertIsDisplayed()
        }
    }

    @Test
    fun factHistoryEmptyPlaceholderIsDisplayed_whenHistoryIsEmpty() {
        val uiState = Result.Success(emptyList<Fact>())

        setUiStateToCompose(uiState)

        composeTestRule.onNodeWithTag(FactHistoryScreenTestTags.EMPTY_HISTORY_PLACEHOLDER)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(
            appContext.getString(R.string.empty_history_placeholder_message)
        ).assertIsDisplayed()
    }

    @Test
    fun loadingIndicatorIsDisplayed_whenFactHistoryIsLoading() {
        val uiState = Result.Loading

        setUiStateToCompose(uiState)

        composeTestRule.onNodeWithTag(CommonComponentTestTags.LOADING_INDICATOR)
            .assertIsDisplayed()
    }

    @Test
    fun loadingIndicatorIsNotDisplayed_whenFactHistoryIsNotLoading() {
        val uiState = Result.Success(emptyList<Fact>())

        setUiStateToCompose(uiState)

        composeTestRule.onNodeWithTag(CommonComponentTestTags.LOADING_INDICATOR)
            .assertIsNotDisplayed()
    }

    @Test
    fun errorIndicatorIsDisplayed_whenFactHistoryIsError() {
        val sampleException = Exception("Sample exception")
        val uiState = Result.Error(sampleException)

        setUiStateToCompose(uiState)

        composeTestRule.onNodeWithTag(CommonComponentTestTags.ERROR_INDICATOR)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleException.message ?: "")
            .assertIsDisplayed()
    }

    @Test
    fun errorIndicatorIsNotDisplayed_whenFactHistoryIsNotError() {
        val uiState = Result.Success(emptyList<Fact>())

        setUiStateToCompose(uiState)

        composeTestRule.onNodeWithTag(CommonComponentTestTags.ERROR_INDICATOR)
            .assertIsNotDisplayed()
    }

    private fun setUiStateToCompose(uiState: Result<List<Fact>>) {
        composeTestRule.setContent {
            FactHistoryScreen(
                uiState = uiState
            )
        }
    }
}
