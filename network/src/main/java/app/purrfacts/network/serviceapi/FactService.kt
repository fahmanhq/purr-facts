package app.purrfacts.network.serviceapi

import app.purrfacts.network.model.FactResponse
import retrofit2.http.GET

interface FactService {
    @GET("fact")
    suspend fun getFact(): FactResponse
}

