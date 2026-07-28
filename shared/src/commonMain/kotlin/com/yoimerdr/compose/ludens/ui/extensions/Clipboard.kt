package com.yoimerdr.compose.ludens.ui.extensions

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.Clipboard

/**
 * Copies the specified plain text to the clipboard.
 *
 * @param text The plain text to copy.
 */
@OptIn(ExperimentalComposeUiApi::class)
expect suspend fun Clipboard.setClipText(text: String)
