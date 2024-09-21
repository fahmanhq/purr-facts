package app.purrfacts.data.impl.datasource.remote

import app.purrfacts.network.model.FactResponse

private val randomFactsResponse = listOf(
    "Unlike dogs, cats do not have a sweet tooth. Scientists believe this is due to a mutation in a key taste receptor.",
    "When a cat chases its prey, it keeps its head level. Dogs and humans bob their heads up and down.",
    "The technical term for a cat’s hairball is a “bezoar.”",
    "A group of cats is called a “clowder.”",
    "A cat can’t climb head first down a tree because every claw on a cat’s paw points the same way. To get down from a tree, a cat must back down.",
).map { FactResponse(it) }

class FakeRemoteFactDataSource : RemoteFactDataSource {

    private var randomFactsIndex = 0

    override suspend fun getNewRandomFact(): FactResponse {
        return randomFactsResponse[randomFactsIndex++ % randomFactsResponse.size]
    }
}