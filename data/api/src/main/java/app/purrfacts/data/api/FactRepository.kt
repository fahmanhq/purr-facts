package app.purrfacts.data.api

import app.purrfacts.data.api.model.Fact

interface FactRepository {

    suspend fun getLastSavedFact(): Fact
    suspend fun getNewFact(): Fact
}