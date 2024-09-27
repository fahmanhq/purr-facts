package app.purrfacts.data.testing.datasource

import app.purrfacts.data.api.model.Fact
import app.purrfacts.data.api.repository.FactRepository

class FakeFactRepository(
    initialFacts: List<Fact> = emptyList()
) : FactRepository {

    private var idx = 0
    private val newFacts = listOf(
        Fact(1, "Cats are curious animals and they love to explore."),
        Fact(2, "Felines are known for their independence and agility."),
        Fact(3, "Cats are curious creatures.")
    )

    private val savedFact = mutableListOf<Fact>().apply { addAll(initialFacts) }

    override suspend fun getLastSavedFact(): Fact {
        return savedFact.lastOrNull() ?: run {
            val fact = getRandomFact()
            savedFact.add(fact)
            fact
        }
    }

    override suspend fun getNewFact(): Fact {
        val fact = getRandomFact()
        savedFact.add(fact)
        return fact
    }

    override suspend fun getAllSavedFacts(): List<Fact> {
        return savedFact
    }

    private fun getRandomFact(): Fact {
        return newFacts[idx++ % newFacts.size]
    }
}