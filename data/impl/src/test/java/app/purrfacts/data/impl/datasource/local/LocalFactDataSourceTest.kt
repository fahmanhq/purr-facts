package app.purrfacts.data.impl.datasource.local

import app.purrfacts.data.api.datasource.LocalFactDataSource
import app.purrfacts.database.dao.FactDao
import app.purrfacts.database.model.FactDbEntity
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LocalFactDataSourceTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @MockK(relaxed = true)
    lateinit var factDao: FactDao

    private lateinit var sut: LocalFactDataSource

    @Before
    fun setup() {
        sut = DefaultLocalFactDataSource(factDao, Dispatchers.Unconfined)
    }

    @Test
    fun `getLastSavedFact should call factDao getLastSavedFact`() = runTest {
        sut.getLastSavedFact()
        coVerify { factDao.getLastSavedFact() }
    }

    @Test
    fun `saveFact should insert the fact into the database and then return the last saved fact`() =
        runTest {
            val factToSave = "Fact 1"
            sut.saveFact(factToSave)

            val savedDbEntity = slot<FactDbEntity>()
            coVerify { factDao.insertFact(capture(savedDbEntity)) }
            assertThat(savedDbEntity.captured.fact).isEqualTo(factToSave)
            coVerify { factDao.getLastSavedFact() }
        }

    @Test
    fun `getAllSavedFacts should call factDao getAllFacts`() = runTest {
        sut.getAllSavedFacts()
        coVerify { factDao.getAllFacts() }
    }
}