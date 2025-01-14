package app.purrfacts.data.api.repository

import app.purrfacts.data.api.model.Fact

interface FactRepository {

    suspend fun getLastSavedFact(): Fact
    suspend fun getNewFact(): Fact
    suspend fun getAllSavedFacts(): List<Fact>
}