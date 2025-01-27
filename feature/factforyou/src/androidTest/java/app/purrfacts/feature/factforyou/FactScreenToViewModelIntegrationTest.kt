package app.purrfacts.feature.factforyou

import android.content.Context
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.purrfacts.core.logger.FakeAppLogger
import app.purrfacts.core.testing.android.hasTestTag
import app.purrfacts.core.testing.android.onNodeWithTag
import app.purrfacts.core.ui.component.CommonComponentTestTags
import app.purrfacts.data.testing.datasource.FakeFactRepository
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import app.purrfacts.core.ui.R as CoreUiR

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class FactScreenToViewModelIntegrationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val appContext: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var viewModel: FactViewModel

    @Before
    fun setUp() {
        viewModel = FactViewModel(
            factRepository = FakeFactRepository(),
            appLogger = FakeAppLogger()
        )

        composeTestRule.setContent {
            FactScreen(viewModel)
        }
    }

    @Test
    fun loadingIndicatorIsDisplayed_whenUiStateIsLoading() {
        viewModel.uiState = FactUiState.Loading

        composeTestRule
            .onNodeWithTag(CommonComponentTestTags.LOADING_INDICATOR)
            .assertIsDisplayed()
    }

    @Test
    fun errorIndicatorIsDisplayed_whenUiStateIsError() {
        val sampleErrorMessageResId = CoreUiR.string.error_msg_unknown_issue
        viewModel.uiState = FactUiState.Error(sampleErrorMessageResId)

        composeTestRule
            .onNodeWithTag(CommonComponentTestTags.ERROR_INDICATOR)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(appContext.getString(sampleErrorMessageResId))
            .assertIsDisplayed()
    }

    @Test
    fun contentIsDisplayed_whenUiStateIsSuccess() {
        composeTestRule.waitUntilDoesNotExist(
            hasTestTag(CommonComponentTestTags.LOADING_INDICATOR)
        )

        composeTestRule
            .onNodeWithTag(FactScreenTestTags.FACT_TEXT)
            .assertIsDisplayed()
    }

    @Test
    fun onUpdateFactBtnClicked_updatesFact() {
        composeTestRule.waitUntilExactlyOneExists(
            hasTestTag(FactScreenTestTags.FACT_TEXT)
        )
        val initialFactText = (viewModel.uiState as FactUiState.Success).factSpec.fact

        val updateFactBtnContentDescription = appContext.getString(R.string.update_fact_btn_label)
        composeTestRule
            .onNodeWithContentDescription(updateFactBtnContentDescription)
            .performClick()

        composeTestRule.waitUntilExactlyOneExists(
            hasTestTag(FactScreenTestTags.FACT_TEXT)
        )
        val newFactText = (viewModel.uiState as FactUiState.Success).factSpec.fact
        assertThat(newFactText).isNotEqualTo(initialFactText)
    }
}