package com.yoimerdr.compose.ludens.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
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
import com.yoimerdr.compose.ludens.ui.extensions.modifier.contentPadding
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.accept
import ludens.composeapp.generated.resources.are_sure
import ludens.composeapp.generated.resources.cancel
import org.jetbrains.compose.resources.stringResource

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
 * A confirmation dialog composable that presents a title, a message,
 * and two action buttons (Confirm and Dismiss).
 *
 * This dialog uses a [Dialog] window wrapper containing an [ElevatedCard] to display
 * the content.
 *
 * @param modifier The modifier to be applied to the [ElevatedCard] content container.
 * @param showDialog Controls the visibility of the dialog. If false, the composable is not emitted.
 * @param title The text displayed at the top of the dialog. Defaults to a localized "Are you sure?".
 * @param message The main body text displayed in the center of the dialog.
 * @param confirmText The text label for the positive action button. Defaults to a localized "Accept".
 * @param dismissText The text label for the negative action button. Defaults to a localized "Cancel".
 * @param onConfirm The callback invoked when the positive action button is clicked.
 * @param onDismiss The callback invoked when the negative action button is clicked, or when the dialog is dismissed via external input (back press or clicking outside).
 */
@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    showDialog: Boolean,
    title: String = stringResource(Res.string.are_sure),
    message: String,
    confirmText: String = stringResource(Res.string.accept),
    dismissText: String = stringResource(Res.string.cancel),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            ElevatedCard(
                modifier = modifier,
            ) {
                Column(
                    modifier = Modifier
                        .contentPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )


                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                    )


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = dismissText,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        FilledTonalButton(
                            onClick = onConfirm,
                            modifier = Modifier.weight(1f),
                            shape = MaterialTheme.shapes.medium,
                            colors = ButtonDefaults.buttonColors()
                        ) {
                            Text(
                                text = confirmText,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

