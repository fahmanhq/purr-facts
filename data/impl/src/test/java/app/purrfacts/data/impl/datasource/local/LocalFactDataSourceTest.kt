package app.purrfacts.data.impl.datasource.local

import app.purrfacts.database.dao.FactDao
import app.purrfacts.database.model.FactDbEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LocalFactDataSourceTest {

    private lateinit var factDao: FactDao
    private lateinit var sut: LocalFactDataSource

    @Before
    fun setup() {
        factDao = FakeFactDao()
        sut = DefaultLocalFactDataSource(factDao, Dispatchers.Unconfined)
    }

    @Test
    fun `getLastSavedFact should return the last saved fact`() = runTest {
        val fact1 = FactDbEntity(1, "Fact 1")
        val fact2 = FactDbEntity(2, "Fact 2")

        factDao.insertFact(fact1)
        factDao.insertFact(fact2)

        val actualResult = sut.getLastSavedFact()

        assertThat(actualResult).isEqualTo(fact2)
    }

    @Test
    fun `getLastSavedFact should return null if no facts are saved`() = runTest {
        val actualResult = sut.getLastSavedFact()

        assertThat(actualResult).isNull()
    }

    @Test
    fun `saveFact should insert the fact into the database`() = runTest {
        val factToSave = "Fact 1"
        val savedFact = sut.saveFact(factToSave)

        val actualResult = factDao.getLastSavedFact()
        assertThat(actualResult).isEqualTo(savedFact)
    }
}

class FakeFactDao : FactDao {

    private val facts = mutableListOf<FactDbEntity>()

    override fun getAllFacts(): List<FactDbEntity> {
        return facts
    }

    override fun insertFact(fact: FactDbEntity) {
        facts.add(fact)
    }

    override fun deleteAllFacts() {
        facts.clear()
    }

    override fun getFactById(factId: Int): FactDbEntity? {
        return facts.find { it.id == factId }
    }

    override fun getLastSavedFact(): FactDbEntity? {
        return facts.lastOrNull()
    }

}