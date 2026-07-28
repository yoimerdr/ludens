package com.yoimerdr.compose.ludens.features.settings.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

/**
 * A text component specifically designed for displaying option names in settings screens.
 *
 * This composable provides a standardized way to render setting option labels with
 * consistent typography.
 *
 * @param modifier The modifier to be applied to the text component.
 * @param style The text style to apply.
 * @param text The text content to display as the option name.
 */
@Composable
fun OptionName(
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        style = style
    )
}