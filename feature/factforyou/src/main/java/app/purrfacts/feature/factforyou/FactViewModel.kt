package app.purrfacts.feature.factforyou

import androidx.lifecycle.ViewModel
import app.purrfacts.network.serviceapi.FactServiceProvider
import kotlinx.coroutines.runBlocking

class FactViewModel : ViewModel() {
    fun updateFact(completion: () -> Unit): String =
        runBlocking {
            try {
                FactServiceProvider.provide().getFact().fact
            } catch (e: Throwable) {
                "something went wrong. error = ${e.message}"
            }.also { completion() }
        }
}
