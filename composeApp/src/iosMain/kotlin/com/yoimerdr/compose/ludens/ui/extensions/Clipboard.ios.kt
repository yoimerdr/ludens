package com.yoimerdr.compose.ludens.ui.extensions

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard

@OptIn(ExperimentalComposeUiApi::class)
actual suspend fun Clipboard.setClipText(text: String) {
    this.setClipEntry(ClipEntry.withPlainText(text))
}
