package com.yoimerdr.compose.ludens.ui.components

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

/**
 * Provides a [CompositionLocalProvider] that sets the [LocalContentColor] and merges the given [textStyle]
 * with the current [LocalTextStyle] for the content within the [content] composable.
 *
 * This is useful for theming or overriding text and color properties in a subtree of the UI.
 *
 * Taken from material3 source code.
 *
 * @param contentColor The color to provide as the local content color.
 * @param textStyle The text style to merge with the current local text style.
 * @param content The composable content that will use the provided color and text style.
 */
@Composable
fun ProvideContentColorTextStyle(
    contentColor: Color,
    textStyle: TextStyle,
    content: @Composable () -> Unit,
) {
    val mergedStyle = LocalTextStyle.current.merge(textStyle)
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalTextStyle provides mergedStyle,
        content = content,
    )
}