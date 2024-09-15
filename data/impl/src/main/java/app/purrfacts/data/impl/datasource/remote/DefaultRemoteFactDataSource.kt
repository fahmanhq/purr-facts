package app.purrfacts.data.impl.datasource.remote

import app.purrfacts.network.model.FactResponse
import app.purrfacts.network.serviceapi.FactService
import javax.inject.Inject

class DefaultRemoteFactDataSource @Inject constructor(
    private val factService: FactService
) : RemoteFactDataSource {

    override suspend fun getNewRandomFact(): FactResponse {
        return factService.getFact()
    }
}