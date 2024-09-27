package app.purrfacts.network.model

import kotlinx.serialization.Serializable

@Serializable
data class FactResponse(
    val fact: String,
)