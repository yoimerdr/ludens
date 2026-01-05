package com.yoimerdr.compose.ludens.ui.components.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.components.layout.Card

/**
 * A customizable side tab component that displays content in a selectable card format.
 *
 * This composable creates a tab-like button that can be used in side navigation or tabbed
 * interfaces. It automatically applies appropriate semantics for accessibility.
 *
 * @param modifier The modifier to be applied to the tab.
 * @param padding The padding to apply to the content inside the tab.
 * Defaults to 16.dp horizontal and 8.dp vertical.
 * @param enabled Whether the tab is enabled for interaction. Defaults to true.
 * @param selected Whether the tab is currently selected. Affects the background color
 * and accessibility state. Defaults to false.
 * @param colors The [CardColors] to use for the tab's appearance. When selected, uses the
 * containerColor; when not selected, uses the disabledContainerColor.
 * Defaults to [CardDefaults.elevatedCardColors].
 * @param interactionSource The [MutableInteractionSource] representing the stream of
 * interactions for this tab. If null, a new one will be created.
 * @param onClick Callback to be invoked when the tab is clicked.
 * @param content The content to be displayed inside the tab, provided as a [ColumnScope] receiver.
 *
 * @see Card
 */
@Composable
fun SideTab(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(16.dp, 8.dp),
    enabled: Boolean = true,
    selected: Boolean = false,
    colors: CardColors = CardDefaults.elevatedCardColors(),
    interactionSource: MutableInteractionSource? = null,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = colors
        .let {
            it.copy(
                containerColor = if (selected) it.containerColor
                else it.disabledContainerColor,
            )
        }

    Card(
        modifier = modifier
            .semantics {
                role = Role.Tab
                this.selected = selected
            },
        onClick = onClick,
        enabled = enabled,
        colors = colors,
        padding = padding,
        content = content,
        interactionSource = interactionSource
    )
}

/**
 * A side tab component with separate start and end composable slots.
 *
 * This is a convenience overload that arranges start and end slots horizontally
 * with a spacing between them.
 *
 * @param modifier The modifier to be applied to the tab.
 * @param enabled Whether the tab is enabled for interaction. Defaults to true.
 * @param selected Whether the tab is currently selected. Affects the background color
 * and accessibility state. Defaults to false.
 * @param colors The [CardColors] to use for the tab's appearance. When selected, uses the
 * containerColor; when not selected, uses the disabledContainerColor.
 * Defaults to [CardDefaults.elevatedCardColors].
 * @param onClick Callback to be invoked when the tab is clicked.
 * @param start The start composable to be displayed.
 * @param end The end composable to be displayed.
 *
 * @see SideTab
 */
@Composable
fun SideTab(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    colors: CardColors = CardDefaults.elevatedCardColors(),
    onClick: () -> Unit,
    start: @Composable RowScope.() -> Unit,
    end: @Composable RowScope.() -> Unit,
) {
    SideTab(
        modifier = modifier,
        enabled = enabled,
        selected = selected,
        colors = colors,
        onClick = onClick,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            start()
            end()
        }
    }
}

/**
 * A side tab component with an [ImageVector] icon and text composable slot.
 *
 * This is a convenience overload that simplifies creating tabs with a vector icon
 * and text.
 *
 * @param modifier The modifier to be applied to the tab.
 * @param enabled Whether the tab is enabled for interaction. Defaults to true.
 * @param selected Whether the tab is currently selected. Affects the background color
 * and accessibility state. Defaults to false.
 * @param colors The [CardColors] to use for the tab's appearance. When selected, uses the
 * containerColor; when not selected, uses the disabledContainerColor.
 * Defaults to [CardDefaults.elevatedCardColors].
 * @param onClick Callback to be invoked when the tab is clicked.
 * @param icon The [ImageVector] to be displayed as the icon.
 * @param iconDescription Optional content description for the icon for accessibility purposes.
 * Defaults to null.
 * @param text The text composable to be displayed, provided as a [RowScope] receiver.
 *
 * @see SideTab
 * @see Icon
 */
@Composable
fun SideTab(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    colors: CardColors = CardDefaults.elevatedCardColors(),
    onClick: () -> Unit,
    icon: ImageVector,
    iconDescription: String? = null,
    text: @Composable RowScope.() -> Unit,
) {
    SideTab(
        modifier = modifier,
        enabled = enabled,
        selected = selected,
        colors = colors,
        onClick = onClick,
        start = {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription,
            )
        },
        end = text
    )
}
