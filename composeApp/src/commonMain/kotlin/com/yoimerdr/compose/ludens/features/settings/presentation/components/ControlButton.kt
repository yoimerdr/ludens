package com.yoimerdr.compose.ludens.features.settings.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.InputKey
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlKeyItemState
import com.yoimerdr.compose.ludens.ui.components.buttons.OutlinedIconButton
import com.yoimerdr.compose.ludens.ui.extensions.modifier.noClickable

/**
 * Displays a basic control button with custom content.
 *
 * This component provides a standardized button container for control elements,
 * wrapping an outlined icon button with fixed dimensions and optional click handling.
 *
 * @param modifier The modifier to be applied to the outer Box container.
 * @param onClick Optional callback invoked when the button is clicked. If null, the button
 * will be non-clickable.
 * @param content The content to be displayed inside the button.
 * @see Modifier.noClickable
 */
@Composable
fun ControlButton(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier.width(48.dp)
    ) {
        OutlinedIconButton(
            modifier = Modifier.size(40.dp), onClick = onClick
        ) {
            Box(
                modifier = Modifier.fillMaxSize().then(
                    if (onClick != null) Modifier
                    else Modifier.noClickable()
                ), contentAlignment = Alignment.Center, content = content
            )
        }
    }
}

/**
 * Displays a control button for a control item.
 *
 * Renders a button showing the simplified name of the control type.
 * The button's appearance is affected by the control's alpha value, and can optionally
 * respond to clicks.
 *
 * @param control The control item state containing type and alpha information.
 * @param modifier The modifier to be applied to the button.
 * @param onClick Optional callback invoked when the button is clicked. If null, the button
 * will be non-clickable.
 */
@Composable
fun ControlButton(
    control: ControlItemState,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    ControlButton(
        modifier = modifier.alpha(control.alpha), onClick = onClick
    ) {
        Text(
            text = control.type.simpleName,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

/**
 * Displays an interactive control button with a dropdown menu for key selection.
 *
 * This component extends the basic control button with a dropdown menu that allows users
 * to select different input keys. When enabled, clicking the button opens a menu showing all
 * available keys, with the currently selected key being disabled in the menu.
 *
 * @param modifier The modifier to be applied to the outer Box container.
 * @param control The control key item state containing the current key selection and control information.
 * @param enabled Whether the button and dropdown menu are enabled for interaction. Defaults to true.
 * @param items The set of available input keys to display in the dropdown menu.
 * @param onSelected Callback invoked when a new input key is selected from the menu.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlButton(
    control: ControlKeyItemState,
    items: Set<InputKey>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onSelected: (InputKey) -> Unit,
) {

    var selected by remember(control) { mutableStateOf(control.key) }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier) {
        ControlButton(
            control,
            onClick = if (enabled) {
                {
                    expanded = true
                }
            } else null
        )
        if (enabled) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.wrapContentWidth()
            ) {
                items.forEach {
                    DropdownMenuItem(
                        text = { Text(it.name) }, onClick = {
                            expanded = false
                            selected = it
                            onSelected(it)
                        }, enabled = selected != it
                    )
                }
            }
        }
    }
}