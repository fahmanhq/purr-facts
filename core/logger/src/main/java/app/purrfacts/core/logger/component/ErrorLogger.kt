package app.purrfacts.core.logger.component

interface ErrorLogger {

    fun logError(exception: Throwable, message: String? = null)

}