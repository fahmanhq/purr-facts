package app.purrfacts.core.logger.component

interface EventLogger {

    fun logEvent(event: String, params: Map<String, String>? = null)
}