package app.purrfacts.core.ui.ext

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Stable
fun Modifier.testTag(tag: Enum<*>) = this.testTag(tag.name)