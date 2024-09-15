package app.purrfacts.data.api

interface FactRepository {

    suspend fun getNewFact() : String
}