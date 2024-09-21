package app.purrfacts.data.impl

import app.purrfacts.data.api.datasource.LocalFactDataSource
import app.purrfacts.data.api.datasource.RemoteFactDataSource
import app.purrfacts.data.api.repository.FactRepository
import app.purrfacts.data.impl.repository.DefaultFactRepository
import app.purrfacts.data.testing.datasource.local.FakeLocalFactDataSource
import app.purrfacts.data.testing.datasource.remote.FakeRemoteFactDataSource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RepositoryToDataSourceIntegrationTest {

    private lateinit var localFactDataSource: LocalFactDataSource
    private lateinit var remoteFactDataSource: RemoteFactDataSource
    private lateinit var repository: FactRepository

    @Before
    fun setup() {
        localFactDataSource = FakeLocalFactDataSource()
        remoteFactDataSource =
            FakeRemoteFactDataSource()
        repository = DefaultFactRepository(localFactDataSource, remoteFactDataSource)
    }

    @Test
    fun `getLastSavedFact should return the last saved fact when there is any fact saved`() =
        runTest {
            val sampleFact = "A Fact"
            localFactDataSource.saveFact(sampleFact)

            val savedFact = repository.getLastSavedFact()
            assertThat(savedFact.fact).isEqualTo(sampleFact)
        }

    @Test
    fun `getLastSavedFact should get & return new fact from remote and also save it to local`() =
        runTest {
            assertThat(localFactDataSource.getLastSavedFact()).isNull()

            val newFact = repository.getLastSavedFact()
            val lastSavedFact = repository.getLastSavedFact()

            assertThat(lastSavedFact).isEqualTo(newFact)
        }

    @Test
    fun `getNewFact should return a new fact from remote and also save it to local`() = runTest {
        val newFact = repository.getNewFact()
        val lastSavedFact = repository.getLastSavedFact()

        assertThat(lastSavedFact).isEqualTo(newFact)
    }
}