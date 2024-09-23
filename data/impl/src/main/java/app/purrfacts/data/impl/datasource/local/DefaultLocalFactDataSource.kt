package app.purrfacts.data.impl.datasource.local

import app.purrfacts.core.common.di.AppDispatchers
import app.purrfacts.core.common.di.Dispatcher
import app.purrfacts.data.api.datasource.LocalFactDataSource
import app.purrfacts.database.dao.FactDao
import app.purrfacts.database.model.FactDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultLocalFactDataSource @Inject constructor(
    private val factDao: FactDao,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : LocalFactDataSource {

    override suspend fun getLastSavedFact(): FactDbEntity? {
        return withContext(ioDispatcher) { factDao.getLastSavedFact() }
    }

    override suspend fun saveFact(fact: String): FactDbEntity {
        return withContext(ioDispatcher) {
            factDao.insertFact(
                FactDbEntity(
                    fact = fact
                )
            )
            factDao.getLastSavedFact()!!
        }
    }

    override suspend fun getAllSavedFacts(): List<FactDbEntity> {
        return withContext(ioDispatcher) { factDao.getAllFacts() }
    }
}