package app.purrfacts.data.impl.repository

import app.purrfacts.data.api.model.Fact
import app.purrfacts.network.model.FactResponse

fun FactResponse.toFact(): Fact =
    Fact(
        id = 0,
        fact = fact,
        length = length
    )