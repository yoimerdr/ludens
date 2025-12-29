package com.yoimerdr.compose.ludens.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Outlined icon button with predefined colors for dark theme.
 *
 * @param modifier Modifier to be applied to the button.
 * @param onClick Callback to be invoked when the button is clicked.
 * @param border The border stroke for the button. Defaults to a 1dp outline color.
 * @param content The content of the button, typically an icon.
 */
@Composable
fun OutlinedIconButton(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    colors: IconButtonColors = IconButtonDefaults.outlinedIconButtonColors(
        contentColor = Color(0xFFE2E2E9),
        containerColor = Color(0xFF111318)
    ),
    border: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    content: @Composable (() -> Unit),
) {
    OutlinedIconButton(
        modifier = modifier,
        onClick = { onClick?.invoke() },
        enabled = true,
        colors = colors,
        border = border,
        content = content
    )
}