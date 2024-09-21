package app.purrfacts.data.impl.datasource.local

import app.purrfacts.database.model.FactDbEntity

class FakeLocalFactDataSource(
    initialFacts: List<FactDbEntity> = emptyList()
) : LocalFactDataSource {

    private val facts = mutableListOf<FactDbEntity>()
    private var dbRowIdx = 0

    init {
        facts.addAll(initialFacts)
        dbRowIdx = facts.size
    }

    override suspend fun getLastSavedFact(): FactDbEntity? {
        return facts.lastOrNull()
    }

    override suspend fun saveFact(fact: String): FactDbEntity {
        val factDbEntity = FactDbEntity(dbRowIdx, fact)
        facts.add(factDbEntity)
        return factDbEntity
    }
}