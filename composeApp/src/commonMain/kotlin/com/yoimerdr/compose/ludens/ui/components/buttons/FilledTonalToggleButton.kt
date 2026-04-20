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
import androidx.compose.ui.graphics.Color

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
    colors: ButtonColors = ButtonDefaults.filledTonalButtonColors(
        disabledContainerColor = Color.Transparent
    ),
    border: BorderStroke? = ButtonDefaults.outlinedButtonBorder(enabled),
    elevation: ButtonElevation? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable (RowScope.() -> Unit),
) {
    val colors = colors.copy(
        containerColor = if (selected)
            colors.containerColor
        else colors.disabledContainerColor
    )
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        elevation = elevation,
        interactionSource = interactionSource,
        content = content,
        shape = MaterialTheme.shapes.medium,
        colors = colors,
        border = border,
    )
}