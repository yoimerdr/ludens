package com.yoimerdr.compose.ludens.ui.components.fields

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yoimerdr.compose.ludens.ui.components.layout.Spaced

/**
 * A spaced field composable that displays a text and a switch.
 *
 * @param modifier The modifier to be applied to the component.
 * @param checked Whether the switch is checked.
 * @param enabled Whether the switch is enabled.
 * @param onCheckedChange A callback that is invoked when the checked state of the switch changes.
 * @param text The composable to be displayed as the label for the switch.
 */
@Composable
fun SwitchField(
    modifier: Modifier = Modifier,
    checked: Boolean = true,
    enabled: Boolean = true,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    text: @Composable RowScope.() -> Unit,
) {
    Spaced(
        modifier = modifier,
        prefix = text
    ) {
        Switch(
            checked = checked,
            enabled = enabled,
            onCheckedChange = onCheckedChange,
        )
    }
}

/**
 * A spaced field composable that displays a text and a switch.
 *
 * @param modifier The modifier to be applied to the component.
 * @param checked Whether the switch is checked.
 * @param enabled Whether the switch is enabled.
 * @param onCheckedChange A callback that is invoked when the checked state of the switch changes.
 * @param text The string to be displayed as the label for the switch.
 */
@Composable
fun SwitchField(
    modifier: Modifier = Modifier,
    checked: Boolean = true,
    enabled: Boolean = true,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    text: String,
) {
    SwitchField(
        modifier = modifier,
        checked = checked,
        enabled = enabled,
        onCheckedChange = onCheckedChange,
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
        }
    )
}
