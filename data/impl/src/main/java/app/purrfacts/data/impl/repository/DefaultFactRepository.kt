package app.purrfacts.data.impl.repository

import app.purrfacts.data.api.FactRepository
import app.purrfacts.data.api.model.Fact
import app.purrfacts.data.impl.datasource.remote.RemoteFactDataSource
import javax.inject.Inject

class DefaultFactRepository @Inject constructor(
    private val remoteFactDataSource: RemoteFactDataSource
) : FactRepository {

    override suspend fun getNewFact(): Fact {
        // GET RANDOM FACT FROM NETWORK
        // SAVE TO DB
        // RETURN THE SAVED FACT
        return remoteFactDataSource.getNewRandomFact().toFact()
    }
}