package app.purrfacts.core.logger.crashlytics

import com.google.common.truth.Truth.assertThat
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CrashlyticsLoggerTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @MockK(relaxed = true)
    lateinit var firebaseCrashlytics: FirebaseCrashlytics

    lateinit var sut: CrashlyticsLogger

    @Before
    fun setUp() {
        sut = CrashlyticsLogger(
            crashlytics = firebaseCrashlytics
        )
    }

    @Test
    fun `logError should log the message and record the exception`() {
        val expectedException = Exception("Test exception")
        val expectedMessage = "Error on specific flow"

        sut.logError(expectedException, expectedMessage)

        verify {
            firebaseCrashlytics.recordException(expectedException)
            firebaseCrashlytics.log(expectedMessage)
        }
    }

    @Test
    fun `logEvent should log the event and the params`() {
        val expectedEvent = "Test event"
        val expectedParams = mapOf("param1" to "value1", "param2" to "value2")

        sut.logEvent(
            event = expectedEvent,
            params = expectedParams
        )

        val actualLoggedMessageSlot = mutableListOf<String>()
        verify { firebaseCrashlytics.log(capture(actualLoggedMessageSlot)) }

        assertThat(actualLoggedMessageSlot).containsExactly(
            sut.getEventLogString(expectedEvent),
            *(expectedParams.map { (key, value) -> sut.getParamLogString(key, value) }
                .toTypedArray())
        )
    }
}