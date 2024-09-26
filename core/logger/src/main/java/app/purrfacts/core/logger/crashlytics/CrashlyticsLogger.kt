package app.purrfacts.core.logger.crashlytics

import androidx.annotation.VisibleForTesting
import app.purrfacts.core.logger.component.ErrorLogger
import app.purrfacts.core.logger.component.EventLogger
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashlyticsLogger(
    private val crashlytics: FirebaseCrashlytics
) : ErrorLogger, EventLogger {

    override fun logError(exception: Throwable, message: String?) {
        with(crashlytics) {
            message?.let(::log)
            recordException(exception)
        }
    }

    override fun logEvent(event: String, params: Map<String, String>?) {
        crashlytics.log(getEventLogString(event))
        params?.forEach { (key, value) ->
            crashlytics.log(getParamLogString(key, value))
        }
    }

    @VisibleForTesting
    internal fun getEventLogString(event: String) = "Event triggered : $event"

    @VisibleForTesting
    internal fun getParamLogString(key: String, value: String) = "Param[$key] = $value"
}
