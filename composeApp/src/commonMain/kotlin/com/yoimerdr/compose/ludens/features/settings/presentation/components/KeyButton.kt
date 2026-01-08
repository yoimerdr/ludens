package com.yoimerdr.compose.ludens.features.settings.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yoimerdr.compose.ludens.ui.components.buttons.OutlinedIconButton
import com.yoimerdr.compose.ludens.ui.extensions.modifier.noClickable

/**
 * A button component designed to display static key control.
 *
 * This composable creates an outlined button specifically styled for showing key action
 * assignments in settings screens. It wraps an [OutlinedIconButton] with a centered box
 * container that fills the button width and prevents click propagation on the content.
 *
 * The content is displayed centered within a non-clickable box, making this ideal for
 * displaying key labels or other interactive elements that need to be visually contained
 * within a button-like appearance.
 *
 * @param modifier The modifier to be applied to the button.
 * @param border The border stroke for the button outline. Defaults to the standard outlined button border.
 * @param content The custom content to display inside the button, provided as a [BoxScope] lambda.
 * This content will be centered and rendered in a non-clickable container.
 */
@Composable
fun KeyActionButton(
    modifier: Modifier = Modifier,
    border: BorderStroke? = ButtonDefaults.outlinedButtonBorder(),
    content: @Composable BoxScope.() -> Unit,
) {
    OutlinedIconButton(
        border = border, modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().noClickable(),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}

/**
 * A button component designed to display static key control.
 *
 * This is a convenience overload of [KeyActionButton] that automatically renders the provided
 * text string in a large title style, suitable for displaying key names (e.g., "X", "Y",
 * "A", "B", etc.) in control settings.
 *
 * The text is styled using MaterialTheme.typography.titleLarge and centered within the button,
 * providing a clear and prominent display of the key binding.
 *
 * @param modifier The modifier to be applied to the button.
 * @param border The border stroke for the button outline. Defaults to the standard outlined button border.
 * @param text The text content to display inside the button, typically representing a key name.
 */
@Composable
fun KeyActionButton(
    modifier: Modifier = Modifier,
    border: BorderStroke? = ButtonDefaults.outlinedButtonBorder(),
    text: String,
) {
    KeyActionButton(
        modifier = modifier,
        border = border,
    ) {
        Text(
            text, style = MaterialTheme.typography.titleLarge
        )
    }
}