package com.yoimerdr.compose.ludens.features.settings.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.ArrowRight

/**
 * A customizable tool option card component for settings screens.
 *
 * This composable creates a standardized settings option layout with an icon and title
 * on the left (prefix) and custom content on the right (suffix). It wraps an [OptionCard]
 * to provide consistent styling across tool-related settings.
 *
 * The prefix section automatically displays a horizontally arranged icon and title,
 * while the suffix section can contain any custom content (switches, buttons, text, etc.).
 *
 * @param modifier The modifier to be applied to the card.
 * @param title The title text to display next to the icon.
 * @param icon The [ImageVector] icon to display at the start of the card.
 * @param iconDescription The content description for the icon, used for accessibility. Can be null if not needed.
 * @param onClick Optional callback to be invoked when the card is clicked. If null, the card is not clickable.
 * @param padding The padding to be applied to the content inside the card. Defaults to medium spacing.
 * @param horizontalAlignment The horizontal alignment of the content within the card column. Defaults to [Alignment.Start].
 * @param enabled Whether the tool option is enabled and can respond to user interactions. Defaults to true.
 * @param shape The shape of the card's container. Defaults to [CardDefaults.elevatedShape].
 * @param colors The colors to be used for the card's container. Defaults to [CardDefaults.elevatedCardColors].
 * @param elevation The elevation configuration for the card. Defaults to [CardDefaults.elevatedCardElevation].
 * @param border Optional border to be drawn around the card.
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for this card.
 * @param content The custom content to be displayed at the end of the card, provided as a [RowScope] lambda.
 * Typically used for switches, buttons, or additional controls.
 */
@Composable
fun ToolOption(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    iconDescription: String?,
    onClick: (() -> Unit)? = null,
    padding: PaddingValues = PaddingValues(LocalSpacing.current.medium),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.elevatedShape,
    colors: CardColors = CardDefaults.elevatedCardColors(),
    elevation: CardElevation = CardDefaults.elevatedCardElevation(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit,
) {
    OptionCard(
        modifier = modifier,
        padding = padding,
        horizontalAlignment = horizontalAlignment,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource,
        enabled = enabled,
        onClick = onClick,
        prefix = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = iconDescription,
                )

                OptionName(
                    text = title,
                )
            }
        },
        suffix = content
    )
}

/**
 * A specialized tool option card with an integrated toggle switch.
 *
 * This composable is a convenience wrapper around [ToolOption] that automatically
 * adds a [Switch] component in the suffix area. It's designed for binary on/off
 * settings where users can toggle a feature or preference.
 *
 * The switch appearance and behavior follow Material3 design guidelines, with
 * the enabled state controlling whether users can interact with the switch.
 *
 * @param modifier The modifier to be applied to the card.
 * @param title The title text to display next to the icon.
 * @param icon The [ImageVector] icon to display at the start of the card.
 * @param iconDescription The content description for the icon, used for accessibility. Can be null if not needed.
 * @param padding The padding to be applied to the content inside the card. Defaults to medium spacing.
 * @param horizontalAlignment The horizontal alignment of the content within the card column. Defaults to [Alignment.Start].
 * @param enabled Whether the switch is enabled and can be toggled by the user. Defaults to true.
 * @param shape The shape of the card's container. Defaults to [CardDefaults.elevatedShape].
 * @param colors The colors to be used for the card's container. Defaults to [CardDefaults.outlinedCardColors].
 * @param elevation The elevation configuration for the card. Defaults to [CardDefaults.elevatedCardElevation].
 * @param border Optional border to be drawn around the card.
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for this card.
 * @param checked The current checked state of the switch. True means on/enabled, false means off/disabled.
 * @param onCheckedChange Callback invoked when the user toggles the switch. Receives the new checked state.
 */
@Composable
fun ToolSwitchOption(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    iconDescription: String?,
    padding: PaddingValues = PaddingValues(LocalSpacing.current.medium),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.elevatedShape,
    colors: CardColors = CardDefaults.outlinedCardColors(),
    elevation: CardElevation = CardDefaults.elevatedCardElevation(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    ToolOption(
        modifier = modifier,
        padding = padding,
        horizontalAlignment = horizontalAlignment,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource,
        title = title,
        icon = icon,
        iconDescription = iconDescription,
    ) {
        Switch(
            checked = checked, onCheckedChange = onCheckedChange, enabled = enabled
        )
    }
}

/**
 * A specialized tool option card with an integrated navigation arrow button.
 *
 * This composable is a convenience wrapper around [ToolOption] that automatically
 * adds an [IconButton] with a right arrow icon in the suffix area. It's designed
 * for settings options that navigate to a detailed configuration screen or perform
 * a specific action when clicked.
 *
 * The card displays a right arrow icon to indicate that clicking will navigate
 * or trigger an action. Both the entire card and the arrow button are clickable
 * and trigger the same action.
 *
 * @param modifier The modifier to be applied to the card.
 * @param title The title text to display next to the icon.
 * @param icon The [ImageVector] icon to display at the start of the card.
 * @param iconDescription The content description for the icon, used for accessibility. Can be null if not needed.
 * @param onClick Optional callback to be invoked when the card or arrow button is clicked.
 * Typically used for navigation to a detail screen or triggering an action.
 * @param padding The padding to be applied to the content inside the card. Defaults to medium spacing.
 * @param horizontalAlignment The horizontal alignment of the content within the card column. Defaults to [Alignment.Start].
 * @param enabled Whether the action button is enabled and can be clicked. Defaults to true.
 * @param shape The shape of the card's container. Defaults to [CardDefaults.elevatedShape].
 * @param colors The colors to be used for the card's container. Defaults to [CardDefaults.outlinedCardColors].
 * @param elevation The elevation configuration for the card. Defaults to [CardDefaults.elevatedCardElevation].
 * @param border Optional border to be drawn around the card.
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for this card.
 */
@Composable
fun ToolActionOption(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    iconDescription: String?,
    onClick: (() -> Unit)? = null,
    padding: PaddingValues = PaddingValues(LocalSpacing.current.medium),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.elevatedShape,
    colors: CardColors = CardDefaults.outlinedCardColors(),
    elevation: CardElevation = CardDefaults.elevatedCardElevation(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    ToolOption(
        modifier = modifier,
        padding = padding,
        horizontalAlignment = horizontalAlignment,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource,
        enabled = enabled,
        onClick = onClick,
        icon = icon,
        iconDescription = iconDescription,
        title = title,
    ) {
        IconButton(
            onClick = {
                onClick?.invoke()
            },
            enabled = enabled
        ) {
            Icon(
                LudensIcons.Default.ArrowRight,
                contentDescription = "Arrow Right",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
