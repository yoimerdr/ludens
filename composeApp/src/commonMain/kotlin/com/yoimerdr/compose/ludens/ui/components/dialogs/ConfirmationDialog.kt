package com.yoimerdr.compose.ludens.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing
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
    showDialog: Boolean,
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.elevatedCardColors(),
    elevation: CardElevation = CardDefaults.elevatedCardElevation(),
    title: String = stringResource(Res.string.are_sure),
    confirmText: String = stringResource(Res.string.accept),
    dismissText: String = stringResource(Res.string.cancel),
    onConfirm: () -> Unit,
) {
    if (showDialog) {
        val spacing = LocalSpacing.current
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            ElevatedCard(
                modifier = modifier,
                colors = colors,
                elevation = elevation,
            ) {
                Column(
                    modifier = Modifier
                        .contentPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(spacing.medium)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )


                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(spacing.medium)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = dismissText,
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
                            )
                        }
                    }
                }
            }
        }
    }
}

