package com.yoimerdr.compose.ludens.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yoimerdr.compose.ludens.app.theme.onSurfaceDark
import com.yoimerdr.compose.ludens.app.theme.surfaceDark

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
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.outlinedIconButtonColors(
        contentColor = onSurfaceDark,
        containerColor = surfaceDark,
        disabledContainerColor = surfaceDark,
    ),
    border: BorderStroke? = ButtonDefaults.outlinedButtonBorder(),
    content: @Composable (() -> Unit),
) {
    OutlinedIconButton(
        modifier = modifier,
        onClick = { onClick?.invoke() },
        enabled = enabled,
        colors = colors,
        border = border,
        content = content
    )
}