package app.purrfacts.data.impl.repository

import app.purrfacts.data.api.FactRepository
import app.purrfacts.data.api.model.Fact
import app.purrfacts.data.impl.datasource.local.LocalFactDataSource
import app.purrfacts.data.impl.datasource.remote.RemoteFactDataSource
import javax.inject.Inject

class DefaultFactRepository @Inject constructor(
    private val localFactDataSource: LocalFactDataSource,
    private val remoteFactDataSource: RemoteFactDataSource
) : FactRepository {

    override suspend fun getLastSavedFact(): Fact {
        val lastSavedFact =
            localFactDataSource.getLastSavedFact() ?: run {
                val newFact = remoteFactDataSource.getNewRandomFact()
                val savedFact = localFactDataSource.saveFact(newFact.fact, newFact.length)
                savedFact
            }
        return lastSavedFact.toFact()
    }

    override suspend fun getNewFact(): Fact {
        val newFact = remoteFactDataSource.getNewRandomFact()
        val savedFact = localFactDataSource.saveFact(newFact.fact, newFact.length)
        return savedFact.toFact()
    }
}