package app.purrfacts.data.impl.datasource.local

import app.purrfacts.database.dao.FactDao
import app.purrfacts.database.model.FactDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultLocalFactDataSource @Inject constructor(
    private val factDao: FactDao
) : LocalFactDataSource {
    override suspend fun getLastSavedFact(): FactDbEntity? {
        return withContext(Dispatchers.IO) { factDao.getLastSavedFact() }
    }

    override suspend fun saveFact(fact: String): FactDbEntity {
        return withContext(Dispatchers.IO) {
            factDao.insertFact(
                FactDbEntity(
                    fact = fact
                )
            )
            factDao.getLastSavedFact()!!
        }
    }
}