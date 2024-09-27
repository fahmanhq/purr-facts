package app.purrfacts.feature.history

import android.content.Context
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.purrfacts.core.logger.FakeAppLogger
import app.purrfacts.core.testing.android.hasTestTag
import app.purrfacts.core.testing.android.onNodeWithTag
import app.purrfacts.core.ui.component.CommonComponentTestTags
import app.purrfacts.data.api.model.Fact
import app.purrfacts.data.testing.datasource.FakeFactRepository
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class FactHistoryScreenToViewModelIntegrationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val appContext: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var viewModel: FactHistoryViewModel

    @Test
    fun loadingIndicatorIsDisplayed_whenUiStateIsLoading() {
        viewModel = createViewModel()
        composeTestRule.setContent {
            FactHistoryScreen(viewModel)
        }
        viewModel.uiState = FactHistoryUiState.Loading

        composeTestRule
            .onNodeWithTag(CommonComponentTestTags.LOADING_INDICATOR)
            .assertIsDisplayed()
    }

    @Test
    fun errorIndicatorIsDisplayed_whenUiStateIsError() {
        viewModel = createViewModel()
        composeTestRule.setContent {
            FactHistoryScreen(viewModel)
        }
        val sampleErrorMessageResId = app.purrfacts.core.ui.R.string.error_msg_unknown_issue
        viewModel.uiState = FactHistoryUiState.Error(sampleErrorMessageResId)

        composeTestRule
            .onNodeWithTag(CommonComponentTestTags.ERROR_INDICATOR)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(appContext.getString(sampleErrorMessageResId))
            .assertIsDisplayed()
    }

    @Test
    fun listIsDisplayed_whenUiStateIsSuccessAndHistoryIsNotEmpty() {
        val factHistory = listOf(
            Fact(1, "Fact 1"),
            Fact(2, "Fact 2"),
            Fact(3, "Fact 3")
        )
        viewModel = createViewModel(initialFacts = factHistory)
        composeTestRule.setContent {
            FactHistoryScreen(viewModel)
        }

        composeTestRule.waitUntilDoesNotExist(
            hasTestTag(CommonComponentTestTags.LOADING_INDICATOR)
        )

        factHistory.forEach { fact ->
            composeTestRule
                .onNodeWithText(fact.fact)
                .assertIsDisplayed()
        }
    }

    @Test
    fun emptyPlaceholderIsDisplayed_whenUiStateIsSuccessAndHistoryIsEmpty() {
        viewModel = createViewModel()
        composeTestRule.setContent {
            FactHistoryScreen(viewModel)
        }
        composeTestRule.waitUntilDoesNotExist(
            hasTestTag(CommonComponentTestTags.LOADING_INDICATOR)
        )

        composeTestRule
            .onNodeWithTag(FactHistoryScreenTestTags.EMPTY_HISTORY_PLACEHOLDER)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(
                appContext.getString(R.string.empty_history_placeholder_message)
            )
            .assertIsDisplayed()
    }

    private fun createViewModel(initialFacts: List<Fact> = emptyList()) = FactHistoryViewModel(
        factRepository = FakeFactRepository(initialFacts),
        appLogger = FakeAppLogger()
    )
}