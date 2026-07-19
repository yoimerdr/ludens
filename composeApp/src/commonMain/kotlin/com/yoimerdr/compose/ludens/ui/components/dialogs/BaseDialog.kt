package com.yoimerdr.compose.ludens.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing
import com.yoimerdr.compose.ludens.ui.extensions.modifier.contentPadding

/**
 * Constrains the width of the composable to a minimum and maximum value.
 *
 * Defaults to a maximum of 500.dp.
 *
 * @param min The minimum width. Defaults to Dp.Unspecified.
 * @param max The maximum width. Defaults to 500.dp.
 *
 * @see Modifier.widthIn
 */
fun Modifier.widthInDialog(
    min: Dp = Dp.Unspecified,
    max: Dp = 500.dp,
) = this.widthIn(min, max)

/**
 * A highly customizable base dialog component that provides consistent styling, card containers,
 * padding, and layout structure for all dialogs in the application.
 *
 * @param showDialog Controls the visibility of the dialog. If false, the composable is not emitted.
 * @param onDismiss Callback invoked when the dialog is dismissed.
 * @param modifier The modifier to be applied to the dialog card.
 * @param colors The card colors to customize container and content colors.
 * @param elevation The elevation of the card container.
 * @param content The composable content scope rendered inside the dialog container.
 */
@Composable
fun BaseDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    colors: CardColors? = null,
    elevation: CardElevation? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (showDialog) {
        val spacing = LocalSpacing.current
        val targetColors = colors ?: CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
        val targetElevation = elevation ?: CardDefaults.cardElevation()

        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            ElevatedCard(
                modifier = modifier,
                colors = targetColors,
                elevation = targetElevation,
            ) {
                Column(
                    modifier = Modifier.contentPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(spacing.medium),
                    content = content
                )
            }
        }
    }
}

/**
 * Renders a standardized dialog title text.
 *
 * @param text The text value of the title.
 * @param modifier The modifier to apply to the text.
 */
@Composable
fun BaseDialogTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

/**
 * Renders standard dialog action buttons row.
 *
 * @param dismissText The label for the negative/dismiss button.
 * @param confirmText The label for the positive/confirm button.
 * @param onDismiss Callback triggered on dismiss button click.
 * @param onConfirm Callback triggered on confirm button click.
 * @param modifier The modifier to apply to the row container.
 */
@Composable
fun BaseDialogActions(
    dismissText: String,
    confirmText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        OutlinedButton(
            onClick = onDismiss,
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = dismissText)
        }

        Button(
            onClick = onConfirm,
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            )
        ) {
            Text(text = confirmText)
        }
    }
}
