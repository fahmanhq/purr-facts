package app.purrfacts.core.logger

import app.purrfacts.core.logger.crashlytics.CrashlyticsLogger
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {

    @Provides
    fun provideCrashlyticsLogger(): CrashlyticsLogger {
        return CrashlyticsLogger(
            FirebaseCrashlytics.getInstance()
        )
    }

    @Provides
    fun provideAppLogger(
        crashlyticsLogger: CrashlyticsLogger,
    ): AppLogger {
        return DefaultAppLogger(
            commonLoggers = setOf(),
            errorLoggers = setOf(crashlyticsLogger),
            eventLoggers = setOf(crashlyticsLogger)
        )
    }
}