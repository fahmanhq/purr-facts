package app.purrfacts.core.testing.android

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.onNodeWithTag

fun SemanticsNodeInteractionsProvider.onNodeWithTag(
    testTag: Enum<*>,
    useUnmergedTree: Boolean = false
) = onNodeWithTag(testTag.name, useUnmergedTree)

fun hasTestTag(testTag: Enum<*>): SemanticsMatcher {
    return androidx.compose.ui.test.hasTestTag(testTag.name)
}