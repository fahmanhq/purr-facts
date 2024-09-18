package app.purrfacts.core.testing.android

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.onNodeWithTag

fun SemanticsNodeInteractionsProvider.onNodeWithTag(
    testTag: Enum<*>,
    useUnmergedTree: Boolean = false
) = onNodeWithTag(testTag.toString(), useUnmergedTree)
