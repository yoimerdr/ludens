package com.yoimerdr.compose.ludens.ui.components.dialogs

import androidx.compose.material3.CardColors
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.accept
import ludens.composeapp.generated.resources.are_sure
import ludens.composeapp.generated.resources.cancel
import org.jetbrains.compose.resources.stringResource

/**
 * A confirmation dialog composable that presents a title, a message,
 * and two action buttons (Confirm and Dismiss).
 *
 * Built leveraging the core [BaseDialog] architecture.
 *
 * @param modifier The modifier to be applied to the content container.
 * @param showDialog Controls the visibility of the dialog. If false, the composable is not emitted.
 * @param title The text displayed at the top of the dialog. Defaults to a localized "Are you sure?".
 * @param message The main body text displayed in the center of the dialog.
 * @param confirmText The text label for the positive action button. Defaults to a localized "Accept".
 * @param dismissText The text label for the negative action button. Defaults to a localized "Cancel".
 * @param onConfirm The callback invoked when the positive action button is clicked.
 * @param onDismiss The callback invoked when the negative action button is clicked.
 */
@Composable
fun ConfirmationDialog(
    showDialog: Boolean,
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    colors: CardColors? = null,
    elevation: CardElevation? = null,
    title: String = stringResource(Res.string.are_sure),
    confirmText: String = stringResource(Res.string.accept),
    dismissText: String = stringResource(Res.string.cancel),
    onConfirm: () -> Unit,
) {
    BaseDialog(
        showDialog = showDialog,
        onDismiss = onDismiss,
        modifier = modifier,
        colors = colors,
        elevation = elevation
    ) {
        BaseDialogTitle(text = title)

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )

        BaseDialogActions(
            dismissText = dismissText,
            confirmText = confirmText,
            onDismiss = onDismiss,
            onConfirm = onConfirm
        )
    }
}
