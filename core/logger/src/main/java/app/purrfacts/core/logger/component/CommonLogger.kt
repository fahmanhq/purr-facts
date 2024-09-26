package app.purrfacts.core.logger.component

interface CommonLogger {

    fun logMessage(message: String, level: LogLevel = LogLevel.INFO)

    enum class LogLevel {
        DEBUG, INFO, WARN, ERROR
    }
}