package com.yoimerdr.compose.ludens.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing
import com.yoimerdr.compose.ludens.ui.extensions.setClipText
import kotlinx.coroutines.launch
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.copy_log
import ludens.composeapp.generated.resources.runtime_error
import ludens.composeapp.generated.resources.restart
import org.jetbrains.compose.resources.stringResource

/**
 * A dialog that displays runtime error messages and stack trace tracebacks from WebView/JavaScript game crashes.
 *
 * Provides options to copy the stack trace traceback to the clipboard and trigger a game restart.
 * Built leveraging the core [BaseDialog] architecture.
 *
 * @param showDialog Controls the visibility of the dialog. If false, the composable is not emitted.
 * @param errorMessage Brief description of the occurred runtime exception.
 * @param stackTrace Complete stack trace logs.
 * @param onDismiss Callback invoked when the dialog is dismissed.
 * @param modifier The modifier to be applied to the dialog card.
 * @param colors The card colors to customize container and content colors.
 * @param elevation The elevation of the card container.
 * @param title The dialog title text. Defaults to a localized "Runtime Error".
 * @param copyText The label text for the copy button. Defaults to a localized "Copy Log".
 * @param restartText The label text for the restart button. Defaults to a localized "Restart".
 * @param onRestart Callback invoked when the restart action is clicked.
 */
@Composable
fun ErrorTracebackDialog(
    showDialog: Boolean,
    errorMessage: String,
    stackTrace: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    colors: CardColors? = null,
    elevation: CardElevation? = null,
    title: String = stringResource(Res.string.runtime_error),
    copyText: String = stringResource(Res.string.copy_log),
    restartText: String = stringResource(Res.string.restart),
    onRestart: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val clipboard = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()

    val formattedStackTrace = remember(stackTrace) {
        val regex = Regex("""[^( ]*www/""")
        stackTrace.lines().joinToString("\n") { line ->
            line.replace(regex, "www/")
        }
    }

    BaseDialog(
        showDialog = showDialog,
        onDismiss = onDismiss,
        modifier = modifier,
        colors = colors,
        elevation = elevation
    ) {
        BaseDialogTitle(text = title)

        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )

        if (stackTrace.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 220.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerLow,
                        shape = MaterialTheme.shapes.medium
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(spacing.medium)
            ) {
                val verticalScrollState = rememberScrollState()
                val horizontalScrollState = rememberScrollState()
                Text(
                    text = formattedStackTrace,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier
                        .verticalScroll(verticalScrollState)
                        .horizontalScroll(horizontalScrollState)
                )
            }
        }

        BaseDialogActions(
            dismissText = copyText,
            confirmText = restartText,
            onDismiss = {
                coroutineScope.launch {
                    clipboard.setClipText(formattedStackTrace)
                }
            },
            onConfirm = onRestart
        )
    }
}
