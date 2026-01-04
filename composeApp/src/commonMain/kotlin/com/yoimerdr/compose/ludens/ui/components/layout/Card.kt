package com.yoimerdr.compose.ludens.ui.components.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * A customizable card component that wraps Material3's Card with additional features.
 *
 * This composable provides a convenient wrapper around Material3's Card that automatically
 * arranges content in a Column with consistent padding and spacing. It supports both
 * clickable and non-clickable variants based on whether an onClick handler is provided.
 *
 * @param modifier The modifier to be applied to the card.
 * @param onClick Optional click handler. If null, creates a non-clickable card; if provided,
 * creates a clickable card with the specified action.
 * @param padding The padding to apply to the content inside the card. Defaults to 16.dp on all sides.
 * @param horizontalAlignment The horizontal alignment of content within the card's column.
 * Defaults to [Alignment.Start].
 * @param enabled Whether the card is enabled for interaction. Only applies when onClick is provided.
 * Defaults to true.
 * @param shape The shape of the card's container. Defaults to [CardDefaults.elevatedShape].
 * @param colors The colors to be used for the card's container and content.
 * Defaults to [CardDefaults.elevatedCardColors].
 * @param elevation The elevation to apply to the card. Defaults to [CardDefaults.elevatedCardElevation].
 * @param border Optional border to draw around the card. Defaults to null (no border).
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions
 * for this card. Only applies when onClick is provided.
 * @param content The content to be displayed inside the card, provided as a [ColumnScope] receiver.
 * Content items are automatically spaced apart vertically.
 *
 * @see androidx.compose.material3.Card
 */
@Composable
fun Card(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    padding: PaddingValues = PaddingValues(16.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.elevatedShape,
    colors: CardColors = CardDefaults.elevatedCardColors(),
    elevation: CardElevation = CardDefaults.elevatedCardElevation(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val wrapper: @Composable ColumnScope.() -> Unit = {
        Column(
            modifier = Modifier
                .padding(padding),
            content = content,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = horizontalAlignment,
        )
    }

    if (onClick == null) {
        Card(
            modifier = modifier,
            content = wrapper,
            colors = colors,
            shape = shape,
            elevation = elevation,
            border = border,
        )
    } else {
        Card(
            onClick = onClick,
            modifier = modifier,
            content = wrapper,
            colors = colors,
            enabled = enabled,
            shape = shape,
            elevation = elevation,
            border = border,
            interactionSource = interactionSource,
        )
    }
}