package com.yoimerdr.compose.ludens.ui.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.components.layout.Card
import com.yoimerdr.compose.ludens.ui.components.provider.ProvideContentColorTextStyle

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
 * @param colors The [ButtonColors] to use for the tab's appearance. When selected, uses the
 * containerColor; when not selected, uses the disabledContainerColor.
 * Defaults to [ButtonDefaults.filledTonalButtonColors].
 * @param interactionSource The [MutableInteractionSource] representing the stream of
 * interactions for this tab. If null, a new one will be created.
 * @param onClick Callback to be invoked when the tab is clicked.
 * @param content The content to be displayed inside the tab, provided as a [RowScope] receiver.
 *
 * @see Card
 */
@Composable
fun SideTab(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(16.dp, 8.dp),
    enabled: Boolean = true,
    selected: Boolean = false,
    colors: ButtonColors = ButtonDefaults.filledTonalButtonColors(
        disabledContainerColor = Color.Transparent
    ),
    interactionSource: MutableInteractionSource? = null,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {

    val colors = colors
        .let {
            it.copy(
                containerColor = if (selected) it.containerColor
                else it.disabledContainerColor,
            )
        }

    FilledTonalButton(
        onClick = onClick,
        modifier = modifier
            .semantics {
                role = Role.Tab
                this.selected = selected
            },
        interactionSource = interactionSource,
        enabled = enabled,
        colors = colors,
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .defaultMinSize(
                    minWidth = ButtonDefaults.MinWidth,
                    minHeight = ButtonDefaults.MinHeight,
                ).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

/**
 * A side tab component with separate start and end composable slots.
 *
 * This is a convenience overload that arranges start and end slots horizontally
 * with a spacing between them when expanded, or vertically as an icon button with
 * text below when collapsed.
 *
 * @param modifier The modifier to be applied to the tab.
 * @param enabled Whether the tab is enabled for interaction. Defaults to true.
 * @param expanded Whether the tab should be displayed in expanded mode (horizontal layout)
 * or collapsed mode (vertical icon button layout). Defaults to true.
 * @param selected Whether the tab is currently selected. Affects the background color
 * and accessibility state. Defaults to false.
 * @param colors The [ButtonColors] to use for the tab's appearance. When selected, uses the
 * containerColor; when not selected, uses the disabledContainerColor.
 * Defaults to [ButtonDefaults.filledTonalButtonColors].
 * @param onClick Callback to be invoked when the tab is clicked.
 * @param interactionSource The [MutableInteractionSource] representing the stream of
 * interactions for this tab. If null, a new one will be created.
 * @param start The start composable to be displayed (typically an icon).
 * @param end The end composable to be displayed (typically text).
 *
 * @see SideTab
 */
@Composable
fun SideTab(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    expanded: Boolean = true,
    selected: Boolean = false,
    colors: ButtonColors = ButtonDefaults.filledTonalButtonColors(
        disabledContainerColor = Color.Transparent
    ),
    onClick: () -> Unit,
    interactionSource: MutableInteractionSource? = null,
    start: @Composable () -> Unit,
    end: @Composable () -> Unit,
) {
    val source = interactionSource ?: remember { MutableInteractionSource() }
    if (expanded) {
        SideTab(
            modifier = modifier,
            enabled = enabled,
            selected = selected,
            colors = colors,
            onClick = onClick,
            interactionSource = interactionSource,
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                itemVerticalAlignment = Alignment.CenterVertically,
                maxLines = 1,
            ) {
                start()
                end()
            }
        }
    } else {
        Column(
            modifier = modifier
                .clickable(
                    enabled = enabled,
                    interactionSource = source,
                    onClick = onClick,
                    indication = null
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val targetColors = colors
                .let {
                    it.copy(
                        containerColor = if (selected) it.containerColor
                        else it.disabledContainerColor,
                    )
                }
            ProvideContentColorTextStyle(
                textStyle = MaterialTheme.typography.labelLarge,
                contentColor = MaterialTheme.colorScheme.contentColorFor(colors.containerColor)
            ) {
                FilledTonalIconButton(
                    onClick = onClick,
                    modifier = Modifier
                        .semantics {
                            role = Role.Checkbox
                        },
                    interactionSource = source,
                    enabled = enabled,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = targetColors.containerColor,
                        disabledContainerColor = targetColors.disabledContainerColor
                    ),
                    shape = MaterialTheme.shapes.medium,
                    content = start,
                )

                end()
            }
        }
    }
}

/**
 * A side tab component with an [ImageVector] icon and text composable slot.
 *
 * This is a convenience overload that simplifies creating tabs with a vector icon
 * and text. Supports both expanded (horizontal) and collapsed (vertical) layouts.
 *
 * @param modifier The modifier to be applied to the tab.
 * @param enabled Whether the tab is enabled for interaction. Defaults to true.
 * @param expanded Whether the tab should be displayed in expanded mode (horizontal layout)
 * or collapsed mode (vertical icon button layout). Defaults to true.
 * @param selected Whether the tab is currently selected. Affects the background color
 * and accessibility state. Defaults to false.
 * @param colors The [ButtonColors] to use for the tab's appearance. When selected, uses the
 * containerColor; when not selected, uses the disabledContainerColor.
 * Defaults to [ButtonDefaults.filledTonalButtonColors].
 * @param onClick Callback to be invoked when the tab is clicked.
 * @param interactionSource The [MutableInteractionSource] representing the stream of
 * interactions for this tab. If null, a new one will be created.
 * @param icon The [ImageVector] to be displayed as the icon.
 * @param iconDescription Optional content description for the icon for accessibility purposes.
 * Defaults to null.
 * @param text The text composable to be displayed.
 *
 * @see SideTab
 * @see Icon
 */
@Composable
fun SideTab(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    expanded: Boolean = true,
    selected: Boolean = false,
    colors: ButtonColors = ButtonDefaults.filledTonalButtonColors(
        disabledContainerColor = Color.Transparent
    ),
    onClick: () -> Unit,
    interactionSource: MutableInteractionSource? = null,
    icon: ImageVector,
    iconDescription: String? = null,
    text: @Composable () -> Unit,
) {
    SideTab(
        modifier = modifier,
        enabled = enabled,
        selected = selected,
        expanded = expanded,
        colors = colors,
        onClick = onClick,
        interactionSource = interactionSource,
        start = {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription,
            )
        },
        end = text
    )
}