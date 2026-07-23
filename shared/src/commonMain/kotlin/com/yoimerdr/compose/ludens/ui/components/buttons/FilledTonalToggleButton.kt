package com.yoimerdr.compose.ludens.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.yoimerdr.compose.ludens.ui.components.provider.LocalStrokes

/**
 * A filled tonal button that can be toggled between selected and unselected states.
 *
 * This component provides a toggle button with Material 3 filled tonal styling.
 * When selected, it displays the container color; when not selected, it displays
 * the disabled container color, creating a visual toggle effect. The button maintains
 * its enabled/disabled state independently from the selected state.
 *
 * @param onClick Callback invoked when the button is clicked.
 * @param modifier The modifier to be applied to the button.
 * @param enabled Whether the button is enabled for interaction. Defaults to `true`.
 * @param selected Whether the button is in the selected state. When `true`, uses the
 * `containerColor`; when `false`, uses the `disabledContainerColor`. Defaults to `false`.
 * @param colors The [ButtonColors] to use for the button's appearance. The selected state
 * determines which color is used: `containerColor` for selected, `disabledContainerColor`
 * for unselected. Defaults to [ButtonDefaults.filledTonalButtonColors] with transparent
 * disabled container.
 * @param border Optional [BorderStroke] to draw around the button. Defaults to
 * [ButtonDefaults.outlinedButtonBorder] based on the enabled state.
 * @param elevation Optional [ButtonElevation] to apply elevation to the button.
 * Defaults to `null` for no elevation.
 * @param shape The shape of the button. Defaults to the theme medium shape.
 * @param interactionSource The [MutableInteractionSource] representing the stream of
 * interactions for this button. If `null`, a new one will be created internally.
 * @param content The content to be displayed inside the button, provided as a [RowScope] receiver.
 *
 * @see FilledTonalButton
 * @see ButtonDefaults.filledTonalButtonColors
 */
@Composable
fun FilledTonalToggleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    colors: ButtonColors? = null,
    border: BorderStroke? = null,
    elevation: ButtonElevation? = null,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape? = null,
    content: @Composable (RowScope.() -> Unit),
) {
    val strokes = LocalStrokes.current
    val palette = colors ?: ButtonDefaults.filledTonalButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        disabledContentColor = MaterialTheme.colorScheme.onSurface,
    )
    val resolvedColors = palette.copy(
        containerColor = if (selected) palette.containerColor else palette.disabledContainerColor,
        contentColor = if (selected) palette.contentColor else palette.disabledContentColor,
    )
    val resolvedBorder = border ?: BorderStroke(
        width = strokes.thin,
        color = if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.outlineVariant,
    )

    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        elevation = elevation,
        interactionSource = interactionSource,
        content = content,
        shape = shape ?: MaterialTheme.shapes.medium,
        colors = resolvedColors,
        border = resolvedBorder,
    )
}
