package app.purrfacts.data.impl

import app.purrfacts.data.api.FactRepository
import app.purrfacts.network.serviceapi.FactService
import javax.inject.Inject

class DefaultFactRepository @Inject constructor(
    private val factService: FactService
) : FactRepository {
    override suspend fun getNewFact(): String {
        return factService.getFact().fact
    }
}