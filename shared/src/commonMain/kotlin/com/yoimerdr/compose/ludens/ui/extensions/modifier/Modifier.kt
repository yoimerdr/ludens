package com.yoimerdr.compose.ludens.ui.extensions.modifier

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

/**
 * Makes a composable non-clickable by consuming all tap gestures.
 */
fun Modifier.noClickable() = this.pointerInput(Unit) {
    detectTapGestures { }
}