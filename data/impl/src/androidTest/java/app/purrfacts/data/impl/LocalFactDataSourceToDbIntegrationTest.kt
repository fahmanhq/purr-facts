package app.purrfacts.data.impl

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.purrfacts.data.impl.datasource.local.DefaultLocalFactDataSource
import app.purrfacts.data.impl.datasource.local.LocalFactDataSource
import app.purrfacts.database.PurrFactsDb
import app.purrfacts.database.dao.FactDao
import app.purrfacts.database.model.FactDbEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalFactDataSourceToDbIntegrationTest {

    private lateinit var db: PurrFactsDb
    private lateinit var factDao: FactDao
    private lateinit var sut: LocalFactDataSource

    @Before
    fun setup() {
        db = PurrFactsDb.createInMemoryDb(
            appContext = ApplicationProvider.getApplicationContext()
        )
        factDao = db.factDao()
        sut = DefaultLocalFactDataSource(factDao, Dispatchers.Unconfined)
    }

    @Test
    fun getLastSavedFact_whenDbEmpty_shouldReturnNull() = runTest {
        assertThat(sut.getLastSavedFact()).isNull()
    }

    @Test
    fun getLastSavedFact_whenDbNotEmpty_shouldReturnLastSavedFact() = runTest {
        val factToSave = "Fact 1"
        factDao.insertFact(FactDbEntity(fact = factToSave))

        val actualResult = sut.getLastSavedFact()

        assertThat(actualResult).isNotNull()
        assertThat(actualResult!!.fact).isEqualTo(factToSave)
    }

    @Test
    fun saveFact_shouldInsertFactIntoDb() = runTest {
        val factToSave = "Fact 1"
        val savedFact = sut.saveFact(factToSave)

        val actualResult = factDao.getLastSavedFact()
        assertThat(actualResult).isEqualTo(savedFact)
    }
}