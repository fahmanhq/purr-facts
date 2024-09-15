package app.purrfacts.data.impl.datasource.local

import app.purrfacts.database.model.FactDbEntity

interface LocalFactDataSource {
    suspend fun getLastSavedFact() : FactDbEntity?
    suspend fun saveFact(fact: String, length: Int): FactDbEntity
}