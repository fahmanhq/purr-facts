package app.purrfacts.data.impl.datasource.remote

import app.purrfacts.data.api.datasource.RemoteFactDataSource
import app.purrfacts.network.model.FactResponse
import app.purrfacts.network.serviceapi.FactService
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RemoteFactDataSourceTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var factService: FactService

    private lateinit var sut: RemoteFactDataSource

    @Before
    fun setup() {
        sut = DefaultRemoteFactDataSource(factService)
    }

    @Test
    fun `getFact should return a fact from the service`() = runTest {
        val expectedFact = FactResponse("test fact")
        coEvery { factService.getFact() } returns expectedFact

        val actualResult = sut.getNewRandomFact()

        assertThat(actualResult)
            .isEqualTo(expectedFact)
    }

    @Test(expected = RuntimeException::class)
    fun `getFact should throw an exception when API call fails`() = runTest {

        coEvery { factService.getFact() } throws RuntimeException("Network error")

        sut.getNewRandomFact()
    }
}
