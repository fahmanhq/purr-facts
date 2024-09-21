package app.purrfacts.data.api.datasource

import app.purrfacts.network.model.FactResponse

interface RemoteFactDataSource {

    suspend fun getNewRandomFact() : FactResponse
}