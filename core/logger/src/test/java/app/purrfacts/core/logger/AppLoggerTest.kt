package app.purrfacts.core.logger

import app.purrfacts.core.logger.component.CommonLogger
import app.purrfacts.core.logger.component.ErrorLogger
import app.purrfacts.core.logger.component.EventLogger
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppLoggerTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @MockK(relaxed = true)
    lateinit var commonLogger: CommonLogger

    @MockK(relaxed = true)
    lateinit var commonLogger2: CommonLogger

    @MockK(relaxed = true)
    lateinit var errorLogger: ErrorLogger

    @MockK(relaxed = true)
    lateinit var errorLogger2: ErrorLogger

    @MockK(relaxed = true)
    lateinit var eventLogger: EventLogger

    @MockK(relaxed = true)
    lateinit var eventLogger2: EventLogger

    lateinit var sut: AppLogger

    @Before
    fun setUp() {
        sut = DefaultAppLogger(
            commonLoggers = setOf(commonLogger, commonLogger2),
            errorLoggers = setOf(errorLogger, errorLogger2),
            eventLoggers = setOf(eventLogger, eventLogger2)
        )
    }

    @Test
    fun `logMessage logs to all commonLoggers`() {
        val expectedMessage = "Sample Message"
        val expectedLevel = CommonLogger.LogLevel.INFO
        sut.logMessage(expectedMessage, expectedLevel)

        verify { commonLogger.logMessage(expectedMessage, expectedLevel) }
        verify { commonLogger2.logMessage(expectedMessage, expectedLevel) }
    }

    @Test
    fun `logError logs to all errorLoggers`() {
        val expectedException = Exception("Sample Error")
        val expectedMessage = "Error on some specific flow"

        sut.logError(expectedException, expectedMessage)

        verify { errorLogger.logError(expectedException, expectedMessage) }
        verify { errorLogger2.logError(expectedException, expectedMessage) }
    }

    @Test
    fun `logEvent logs to all eventLoggers`() {
        val expectedEvent = "Sample Event"
        val expectedProperties = mapOf("key1" to "value1", "key2" to "value2")
        sut.logEvent(expectedEvent, expectedProperties)

        verify { eventLogger.logEvent(expectedEvent, expectedProperties) }
        verify { eventLogger2.logEvent(expectedEvent, expectedProperties) }
    }

}