package app.purrfacts.data.impl.repository

import app.purrfacts.data.api.model.Fact
import app.purrfacts.database.model.FactDbEntity

fun FactDbEntity.toFact(): Fact =
    Fact(
        id = id,
        fact = fact,
    )