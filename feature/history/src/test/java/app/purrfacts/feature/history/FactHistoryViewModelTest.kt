package app.purrfacts.feature.history

import app.purrfacts.core.logger.AppLogger
import app.purrfacts.core.testing.MainDispatcherRule
import app.purrfacts.core.ui.R
import app.purrfacts.data.api.model.Fact
import app.purrfacts.data.api.repository.FactRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FactHistoryViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var factRepository: FactRepository

    @MockK(relaxed = true)
    lateinit var appLogger: AppLogger

    private lateinit var sut: FactHistoryViewModel

    @Before
    fun setup() {
        sut = FactHistoryViewModel(factRepository, appLogger)
    }

    @Test
    fun `loadFactHistory loads fact history and set as new ui state`() {
        val history = listOf(
            Fact(id = 0, "Fact 1"),
            Fact(id = 1, "Fact 2")
        )
        coEvery { factRepository.getAllSavedFacts() } returns history
        sut.loadFactHistory()
        assertThat(sut.uiState)
            .isEqualTo(FactHistoryUiState.Success(history))
    }

    @Test
    fun `loadFactHistory should not re-request fact history if it is not init`() {
        sut.isInit = false

        sut.loadFactHistory()

        coVerify(exactly = 0) { factRepository.getAllSavedFacts() }
    }

    @Test
    fun `loadFactHistory should set ui state to error if error occurred and isInit still set to true`() {
        val sampleException = Exception("Sample error")
        coEvery { factRepository.getAllSavedFacts() } throws sampleException

        sut.loadFactHistory()
        assertThat(sut.uiState)
            .isEqualTo(FactHistoryUiState.Error(R.string.error_msg_unknown_issue))
        assertThat(sut.isInit).isTrue()

        verify { appLogger.logError(sampleException, any()) }
    }
}