package app.purrfacts.data.api.datasource

import app.purrfacts.database.model.FactDbEntity

interface LocalFactDataSource {
    suspend fun getLastSavedFact(): FactDbEntity?
    suspend fun saveFact(fact: String): FactDbEntity
    suspend fun getAllSavedFacts(): List<FactDbEntity>
}