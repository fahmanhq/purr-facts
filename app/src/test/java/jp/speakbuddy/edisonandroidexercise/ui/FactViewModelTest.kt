package jp.speakbuddy.edisonandroidexercise.ui

import org.junit.Test

class FactViewModelTest {

    private val viewModel = app.purrfacts.feature.factforyou.FactViewModel()

    @Test
    fun updateFact() {
        var loading = true
        val initialFact = "initial"
        var fact = initialFact

        fact = viewModel.updateFact()

        assert(!loading)
        assert(fact != initialFact)
    }
}
