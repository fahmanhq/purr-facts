package app.purrfacts.feature.history

import app.purrfacts.core.testing.MainDispatcherRule
import app.purrfacts.core.ui.Result
import app.purrfacts.data.api.model.Fact
import app.purrfacts.data.api.repository.FactRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
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

    private lateinit var sut: FactHistoryViewModel

    @Before
    fun setup() {
        sut = FactHistoryViewModel(factRepository)
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
            .isEqualTo(Result.Success(history))
    }

    @Test
    fun `loadFactHistory should not re-request fact history if it is not init`() {
        sut.isInit = false

        sut.loadFactHistory()

        coVerify(exactly = 0) { factRepository.getAllSavedFacts() }
    }

    @Test
    fun `loadFactHistory should set ui state to error if error occurred`() {
        val sampleException = Exception("Sample error")
        coEvery { factRepository.getAllSavedFacts() } throws sampleException

        sut.loadFactHistory()
        assertThat(sut.uiState)
            .isEqualTo(Result.Error(sampleException))
    }
}