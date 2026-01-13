package com.yoimerdr.compose.ludens.features.settings.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import com.yoimerdr.compose.ludens.ui.components.layout.Card
import com.yoimerdr.compose.ludens.ui.components.layout.Spaced
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing

/**
 * A customizable card component designed for displaying settings options.
 *
 * This composable wraps the base [Card] component with sensible defaults for settings screens,
 * providing a consistent elevated card appearance with configurable content layout.
 *
 * @param modifier The modifier to be applied to the card.
 * @param onClick Optional callback to be invoked when the card is clicked. If null, the card is not clickable.
 * @param padding The padding to be applied to the content inside the card. Defaults to medium spacing.
 * @param horizontalAlignment The horizontal alignment of the content within the card column. Defaults to [Alignment.Start].
 * @param enabled Whether the card is enabled and can respond to user interactions. Defaults to true.
 * @param shape The shape of the card's container. Defaults to [CardDefaults.elevatedShape].
 * @param colors The colors to be used for the card's container. Defaults to [CardDefaults.elevatedCardColors].
 * @param elevation The elevation configuration for the card. Defaults to [CardDefaults.elevatedCardElevation].
 * @param border Optional border to be drawn around the card.
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for this card.
 * @param content The content to be displayed inside the card, provided as a [ColumnScope] lambda.
 */
@Composable
fun OptionCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    padding: PaddingValues = PaddingValues(LocalSpacing.current.medium),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.elevatedShape,
    colors: CardColors = CardDefaults.elevatedCardColors(),
    elevation: CardElevation = CardDefaults.elevatedCardElevation(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        padding = padding,
        horizontalAlignment = horizontalAlignment,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource,
        content = content,
    )
}

/**
 * A customizable card component designed for displaying settings options with prefix and suffix content.
 *
 * This variant of [OptionCard] provides a convenient way to create cards with a horizontal layout
 * containing prefix (start) and suffix (end) content areas, using the [Spaced] component internally
 * to manage the layout.
 *
 * @param modifier The modifier to be applied to the card.
 * @param onClick Optional callback to be invoked when the card is clicked. If null, the card is not clickable.
 * @param padding The padding to be applied to the content inside the card. Defaults to medium spacing.
 * @param horizontalAlignment The horizontal alignment of the content within the card column. Defaults to [Alignment.Start].
 * @param enabled Whether the card is enabled and can respond to user interactions. Defaults to true.
 * @param shape The shape of the card's container. Defaults to [CardDefaults.elevatedShape].
 * @param colors The colors to be used for the card's container. Defaults to [CardDefaults.elevatedCardColors].
 * @param elevation The elevation configuration for the card. Defaults to [CardDefaults.elevatedCardElevation].
 * @param border Optional border to be drawn around the card.
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for this card.
 * @param prefix The content to be displayed at the start (left) of the card, provided as a [RowScope] lambda.
 * @param suffix The content to be displayed at the end (right) of the card, provided as a [RowScope] lambda.
 */
@Composable
fun OptionCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    padding: PaddingValues = PaddingValues(LocalSpacing.current.medium),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.elevatedShape,
    colors: CardColors = CardDefaults.elevatedCardColors(),
    elevation: CardElevation = CardDefaults.elevatedCardElevation(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    prefix: @Composable RowScope.() -> Unit,
    suffix: @Composable RowScope.() -> Unit,
) {
    OptionCard(
        modifier = modifier,
        onClick = onClick,
        padding = padding,
        horizontalAlignment = horizontalAlignment,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource,
    ) {
        Spaced(
            modifier = Modifier.fillMaxWidth(),
            prefix = prefix,
            suffix = suffix
        )
    }
}

/**
 * A specialized card component for displaying settings options with an icon, title, and custom content.
 *
 * This high-level variant of [OptionCard] provides a standardized layout for settings options,
 * automatically positioning an icon and title text at the start (prefix), with custom content
 * at the end (suffix).
 *
 * @param modifier The modifier to be applied to the card.
 * @param title The title text to be displayed next to the icon.
 * @param icon The [ImageVector] icon to be displayed at the start of the card.
 * @param iconDescription The content description for the icon, used for accessibility. Can be null if not needed.
 * @param onClick Optional callback to be invoked when the card is clicked. If null, the card is not clickable.
 * @param padding The padding to be applied to the content inside the card. Defaults to medium spacing.
 * @param horizontalAlignment The horizontal alignment of the content within the card column. Defaults to [Alignment.Start].
 * @param enabled Whether the card is enabled and can respond to user interactions. Defaults to true.
 * @param shape The shape of the card's container. Defaults to [CardDefaults.elevatedShape].
 * @param colors The colors to be used for the card's container. Defaults to [CardDefaults.elevatedCardColors].
 * @param elevation The elevation configuration for the card. Defaults to [CardDefaults.elevatedCardElevation].
 * @param border Optional border to be drawn around the card.
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for this card.
 * @param content The custom content to be displayed at the end of the card, provided as a [RowScope] lambda.
 * Typically used for switches, buttons, or additional information.
 */
@Composable
fun OptionCard(
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
        onClick = onClick,
        padding = padding,
        horizontalAlignment = horizontalAlignment,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource,
        prefix = {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription,
            )

            OptionName(
                text = title
            )
        },
        suffix = content
    )
}

/**
 * A container component for displaying a vertical list of settings options.
 *
 * This composable wraps a [LazyColumn] with sensible defaults for settings screens,
 * providing consistent spacing between option items. It's designed to hold multiple
 * [OptionCard] components or other settings-related UI elements.
 *
 * @param modifier The modifier to be applied to the lazy column.
 * @param verticalArrangement The vertical arrangement of items in the column. Defaults to medium spacing between items.
 * @param horizontalAlignment The horizontal alignment of items within the column. Defaults to [Alignment.Start].
 * @param state The [LazyListState] to control and observe the scrolling state. Defaults to a remembered state.
 * @param content The content to be displayed in the lazy column, provided as a [LazyListScope] lambda.
 * Use this to add items, stickyHeaders, or other lazy list elements.
 */
@Composable
fun OptionsContainer(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(LocalSpacing.current.medium),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    state: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}

