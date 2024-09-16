package app.purrfacts.data.impl.repository

import app.purrfacts.data.api.datasource.LocalFactDataSource
import app.purrfacts.data.api.datasource.RemoteFactDataSource
import app.purrfacts.data.api.model.Fact
import app.purrfacts.data.api.repository.FactRepository
import javax.inject.Inject

class DefaultFactRepository @Inject constructor(
    private val localFactDataSource: LocalFactDataSource,
    private val remoteFactDataSource: RemoteFactDataSource
) : FactRepository {

    override suspend fun getLastSavedFact(): Fact {
        val lastSavedFact =
            localFactDataSource.getLastSavedFact() ?: run {
                val newFact = remoteFactDataSource.getNewRandomFact()
                val savedFact = localFactDataSource.saveFact(newFact.fact)
                savedFact
            }
        return lastSavedFact.toFact()
    }

    override suspend fun getNewFact(): Fact {
        val newFact = remoteFactDataSource.getNewRandomFact()
        val savedFact = localFactDataSource.saveFact(newFact.fact)
        return savedFact.toFact()
    }

    override suspend fun getAllSavedFacts(): List<Fact> {
        return localFactDataSource.getAllSavedFacts()
            .map { it.toFact() }
    }
}