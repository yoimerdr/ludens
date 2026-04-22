package com.yoimerdr.compose.ludens.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.yoimerdr.compose.ludens.ui.components.provider.LocalStrokes

/**
 * Outlined icon button with predefined colors for dark theme.
 *
 * @param modifier Modifier to be applied to the button.
 * @param onClick Callback to be invoked when the button is clicked.
 * @param border The border stroke for the button. Defaults to a 1dp outline color.
 * @param colors The colors to be used for this button. Defaults to outlinedIconButtonColors with appropriate color scheme values.
 * @param enabled Controls the enabled state of the button. Defaults to true.
 * @param shape The shape of the button. Defaults to IconButtonDefaults.outlinedShape.
 * @param content The content of the button, typically an icon.
 */
@Composable
fun OutlinedIconButton(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    colors: IconButtonColors? = null,
    border: BorderStroke? = null,
    shape: Shape = IconButtonDefaults.outlinedShape,
    content: @Composable (() -> Unit),
) {
    val strokes = LocalStrokes.current
    val targetColors = colors ?: IconButtonDefaults.outlinedIconButtonColors(
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
    )
    val targetBorder = border ?: BorderStroke(
        width = strokes.thin,
        color = MaterialTheme.colorScheme.outlineVariant,
    )

    OutlinedIconButton(
        modifier = modifier,
        onClick = { onClick?.invoke() },
        enabled = enabled,
        colors = targetColors,
        border = targetBorder,
        content = content,
        shape = shape
    )
}
