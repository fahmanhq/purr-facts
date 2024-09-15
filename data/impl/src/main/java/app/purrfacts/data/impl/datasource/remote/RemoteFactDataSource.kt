package app.purrfacts.data.impl.datasource.remote

import app.purrfacts.network.model.FactResponse

interface RemoteFactDataSource {

    suspend fun getNewRandomFact() : FactResponse
}