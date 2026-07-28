package com.yoimerdr.compose.ludens.features.settings.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yoimerdr.compose.ludens.ui.components.buttons.OutlinedIconButton
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.Dismiss

/**
 * A button component designed to close or dismiss settings screens.
 *
 * This composable creates an outlined icon button specifically styled for closing
 * settings sections.
 *
 * @param modifier The modifier to be applied to the button.
 * @param onClick Callback to be invoked when the button is clicked. If `null`,
 * the button will be non-interactive.
 *
 * @see OutlinedIconButton
 */
@Composable
fun CloseIconButton(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)?,
) {
    OutlinedIconButton(
        onClick = onClick, modifier = modifier
    ) {
        Icon(
            LudensIcons.Default.Dismiss,
            contentDescription = "Close",
        )
    }
}