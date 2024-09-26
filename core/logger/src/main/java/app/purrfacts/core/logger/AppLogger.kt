package app.purrfacts.core.logger

import app.purrfacts.core.logger.component.CommonLogger
import app.purrfacts.core.logger.component.ErrorLogger
import app.purrfacts.core.logger.component.EventLogger

interface AppLogger : CommonLogger, EventLogger, ErrorLogger

internal class DefaultAppLogger(
    private val commonLoggers: Set<CommonLogger>,
    private val eventLoggers: Set<EventLogger>,
    private val errorLoggers: Set<ErrorLogger>
) : AppLogger {

    override fun logMessage(message: String, level: CommonLogger.LogLevel) {
        commonLoggers.forEach { it.logMessage(message, level) }
    }

    override fun logEvent(event: String, params: Map<String, String>?) {
        eventLoggers.forEach { it.logEvent(event, params) }
    }

    override fun logError(exception: Throwable, message: String?) {
        errorLoggers.forEach { it.logError(exception, message) }
    }
}

class FakeAppLogger(
    private val logBuffer: MutableList<String> = mutableListOf()
) : AppLogger {

    override fun logMessage(message: String, level: CommonLogger.LogLevel) {
        logBuffer.add(message)
    }

    override fun logEvent(event: String, params: Map<String, String>?) {
        logBuffer.add(event)
    }

    override fun logError(exception: Throwable, message: String?) {
        logBuffer.add(exception.message.orEmpty())
    }
}
