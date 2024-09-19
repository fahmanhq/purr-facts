package app.purrfacts.feature.factforyou

import app.purrfacts.core.testing.MainDispatcherRule
import app.purrfacts.core.ui.Result
import app.purrfacts.data.api.FactRepository
import app.purrfacts.data.api.model.Fact
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FactViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var factRepository: FactRepository

    private lateinit var sut: FactViewModel

    @Before
    fun setup() {
        sut = FactViewModel(factRepository)
    }

    @Test
    fun `loadStartingFact should get the last saved fact and set as new ui state`() {
        val savedFact = Fact(
            id = 1,
            fact = "Last Fact"
        )
        coEvery { factRepository.getLastSavedFact() } returns savedFact

        sut.loadStartingFact()

        coVerify { factRepository.getLastSavedFact() }
        assertThat(sut.uiState).isInstanceOf(Result.Success::class.java)

        val uiState = (sut.uiState as Result.Success).data
        assertThat(uiState.fact).isEqualTo(savedFact.fact)
    }

    @Test
    fun `loadStartingFact should not do anything if it is not the initial load`() {
        sut.isInit = false

        sut.loadStartingFact()
        coVerify(exactly = 0) { factRepository.getLastSavedFact() }
    }

    @Test
    fun `updateFact should get the new fact and set it as new ui state`() {
        val newFact = Fact(
            id = 1,
            fact = "New Fact"
        )
        coEvery { factRepository.getNewFact() } returns newFact

        sut.updateFact()

        coVerify { factRepository.getNewFact() }
        assertThat(sut.uiState).isInstanceOf(Result.Success::class.java)

        val uiState = (sut.uiState as Result.Success).data
        assertThat(uiState.fact).isEqualTo(newFact.fact)
    }

    @Test
    fun `fact with keyword (cats) & length more than 100 should be presented on the ui state correctly`() {
        val fact = "fact with keyword cats and length more than 100".let {
            var longFact = it
            repeat(101 - it.length) { // increase the fact length by adding more space until length > 100
                longFact += " "
            }
            longFact
        }

        val uiState = sut.createFactUiState(fact)
        assertThat(uiState.fact).isEqualTo(fact)
        assertThat(uiState.containsCats).isTrue()
        assertThat(uiState.isLongFact).isTrue()
    }
}