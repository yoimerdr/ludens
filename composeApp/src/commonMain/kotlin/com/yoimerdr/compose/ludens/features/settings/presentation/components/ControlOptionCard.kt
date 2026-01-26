package com.yoimerdr.compose.ludens.features.settings.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.yoimerdr.compose.ludens.ui.components.fields.SwitchField
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing

/**
 * A customizable control option card component for settings screens.
 *
 * This composable serves as a flexible base for building control setting cards in the settings screen.
 * It wraps an [OptionCard] and provides two customizable content areas arranged vertically.
 *
 * @param modifier The modifier to be applied to the card.
 * @param onClick Optional callback to be invoked when the card is clicked. If null, the card is not clickable.
 * @param padding The padding to be applied to the content inside the card. Defaults to medium spacing.
 * @param horizontalAlignment The horizontal alignment of the content within the card column. Defaults to [Alignment.Start].
 * @param shape The shape of the card's container. Defaults to [CardDefaults.elevatedShape].
 * @param colors The colors to be used for the card's container. Defaults to [CardDefaults.outlinedCardColors].
 * @param elevation The elevation configuration for the card. Defaults to [CardDefaults.elevatedCardElevation].
 * @param border Optional border to be drawn around the card.
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for this card.
 * @param header Optional composable content to be displayed at the top of the card, provided as a [ColumnScope] lambda.
 * Typically used for titles, labels, or toggle switches.
 * @param content The composable content to be displayed in the content area, provided as a [RowScope] lambda.
 * This content is wrapped in a Row with center vertical alignment and medium spacing.
 * Typically used for sliders, buttons, or preview samples.
 *
 * @see ControlOptionCard Overload with integrated switch and alpha controls
 */
@Composable
fun ControlOptionCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    padding: PaddingValues = PaddingValues(LocalSpacing.current.medium),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    shape: Shape = CardDefaults.elevatedShape,
    colors: CardColors = CardDefaults.outlinedCardColors(),
    elevation: CardElevation = CardDefaults.elevatedCardElevation(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    header: (@Composable ColumnScope.() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    OptionCard(
        modifier = modifier,
        onClick = onClick,
        padding = padding,
        horizontalAlignment = horizontalAlignment,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource,
    ) {
        header?.invoke(this)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
            content = content
        )
    }
}

/**
 * A specialized option card component for displaying control settings with alpha/opacity adjustments.
 *
 * This composable combines an [OptionCard] with control-specific features including:
 * - An optional toggle switch ([SwitchField]) to enable/disable the control
 * - An alpha/opacity slider ([ControlAlpha]) for adjusting transparency
 * - Optional sample preview content that reflects the alpha changes
 *
 * This component is particularly useful for settings that allow users to customize
 * the visual appearance of on-screen controls (like joysticks or buttons) by adjusting
 * their visibility/opacity.
 *
 * @param modifier The modifier to be applied to the card.
 * @param onClick Optional callback to be invoked when the card is clicked. If null, the card is not clickable.
 * @param padding The padding to be applied to the content inside the card. Defaults to medium spacing.
 * @param horizontalAlignment The horizontal alignment of the content within the card column. Defaults to [Alignment.Start].
 * @param enabled Whether the control settings are enabled. Affects the switch field interaction. Defaults to true.
 * @param shape The shape of the card's container. Defaults to [CardDefaults.elevatedShape].
 * @param colors The colors to be used for the card's container. Defaults to [CardDefaults.outlinedCardColors].
 * @param elevation The elevation configuration for the card. Defaults to [CardDefaults.elevatedCardElevation].
 * @param border Optional border to be drawn around the card.
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for this card.
 * @param enabledAlpha Whether the alpha slider is enabled and can be adjusted. Defaults to true.
 * @param alpha The current alpha/opacity value, typically between 0.0 (transparent) and 1.0 (opaque).
 * @param valueRange The valid range for alpha values. Defaults to 0f..1f.
 * @param alphaSample Optional composable content that serves as a visual preview of the alpha effect.
 * This content will be displayed next to the alpha slider and should reflect the current alpha value.
 * @param onAlphaChange Callback invoked when the alpha value changes via the slider.
 * @param checked The current checked state of the toggle switch. Defaults to false.
 * @param onCheckedChange Callback invoked when the checked state of the toggle switch changes.
 * @param useSwitchField Whether to display a [SwitchField] (true) or a simple [Row] with text (false). Defaults to true.
 * @param text The text content to display, provided as a [RowScope] lambda. When [useSwitchField] is true,
 * this is used as the label for the switch; otherwise, it's displayed in a simple row.
 */
@Composable
fun ControlOptionCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    padding: PaddingValues = PaddingValues(LocalSpacing.current.medium),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.elevatedShape,
    colors: CardColors = CardDefaults.outlinedCardColors(),
    elevation: CardElevation = CardDefaults.elevatedCardElevation(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    enabledAlpha: Boolean = true,
    alpha: Float,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    alphaSample: (@Composable RowScope.() -> Unit)? = null,
    onAlphaChange: (Float) -> Unit,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    useSwitchField: Boolean = true,
    text: @Composable (RowScope.() -> Unit),
) {
    ControlOptionCard(
        modifier = modifier,
        onClick = onClick,
        padding = padding,
        horizontalAlignment = horizontalAlignment,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource,
        header = {
            if (useSwitchField) SwitchField(
                modifier = Modifier.fillMaxWidth(),
                checked = checked,
                enabled = enabled,
                onCheckedChange = onCheckedChange,
                text = text
            )
            else {
                Row(content = text)
            }
        },
    ) {
        alphaSample?.invoke(this)

        ControlAlpha(
            modifier = Modifier.weight(1f),
            value = alpha,
            enabled = enabledAlpha,
            valueRange = valueRange,
            onValueChange = onAlphaChange,
        )
    }
}