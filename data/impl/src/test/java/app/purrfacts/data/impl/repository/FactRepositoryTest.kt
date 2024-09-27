package app.purrfacts.data.impl.repository

import app.purrfacts.data.api.datasource.LocalFactDataSource
import app.purrfacts.data.api.datasource.RemoteFactDataSource
import app.purrfacts.data.api.repository.FactRepository
import app.purrfacts.database.model.FactDbEntity
import app.purrfacts.network.model.FactResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FactRepositoryTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @MockK
    lateinit var remoteFactDataSource: RemoteFactDataSource

    @MockK
    lateinit var localFactDataSource: LocalFactDataSource

    private lateinit var sut: FactRepository

    @Before
    fun setUp() {
        sut = DefaultFactRepository(
            localFactDataSource = localFactDataSource,
            remoteFactDataSource = remoteFactDataSource,
        )
    }

    @Test
    fun `getLastSavedFact should return the last locally stored fact if any`() = runTest {
        val savedFact = FactDbEntity(id = 1, fact = "Fact 1")
        coEvery { localFactDataSource.getLastSavedFact() } returns savedFact

        val actualFact = sut.getLastSavedFact()
        coVerify(exactly = 0) { remoteFactDataSource.getNewRandomFact() }
        coVerify { localFactDataSource.getLastSavedFact() }

        assertThat(actualFact).isEqualTo(savedFact.toFact())
    }

    @Test
    fun `getLastSavedFact should request new fact from remote & cache it locally if no locally stored fact`() = runTest {
        val newFactFromRemote = FactResponse(fact = "New Fact")
        val savedFact = FactDbEntity(id = 1, fact = newFactFromRemote.fact)

        coEvery { localFactDataSource.getLastSavedFact() } returns null
        coEvery { remoteFactDataSource.getNewRandomFact() } returns newFactFromRemote
        coEvery { localFactDataSource.saveFact(any()) } returns savedFact

        val actualFact = sut.getLastSavedFact()
        coVerify { remoteFactDataSource.getNewRandomFact() }
        coVerify { localFactDataSource.saveFact(newFactFromRemote.fact) }

        assertThat(actualFact).isEqualTo(savedFact.toFact())
    }

    @Test
    fun `getNewFact should request new fact from remote & cache it locally`() = runTest {
        val newFactFromRemote = FactResponse(fact = "New Fact")
        val savedFact = FactDbEntity(id = 1, fact = newFactFromRemote.fact)

        coEvery { remoteFactDataSource.getNewRandomFact() } returns newFactFromRemote
        coEvery { localFactDataSource.saveFact(any()) } returns savedFact

        val actualFact = sut.getNewFact()
        coVerify { remoteFactDataSource.getNewRandomFact() }
        coVerify { localFactDataSource.saveFact(newFactFromRemote.fact) }

        assertThat(actualFact).isEqualTo(savedFact.toFact())
    }

    @Test
    fun `getAllSavedFacts should return all saved facts`() = runTest {
        val savedFacts = listOf(
            FactDbEntity(id = 1, fact = "Fact 1"),
            FactDbEntity(id = 2, fact = "Fact 2"),
        )
        coEvery { localFactDataSource.getAllSavedFacts() } returns savedFacts

        val actualFacts = sut.getAllSavedFacts()

        coVerify { localFactDataSource.getAllSavedFacts() }
        assertThat(actualFacts).containsExactlyElementsIn(savedFacts.map { it.toFact() })
    }
}