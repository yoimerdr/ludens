package com.yoimerdr.compose.ludens.app.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yoimerdr.compose.ludens.app.navigation.Destination
import com.yoimerdr.compose.ludens.core.infrastructure.platform.PlatformApplication
import com.yoimerdr.compose.ludens.ui.components.dialogs.ConfirmationDialog
import com.yoimerdr.compose.ludens.ui.components.dialogs.widthInDialog
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.exit_app
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

/**
 * Handles back navigation with a confirmation popup for exiting the application.
 *
 * This composable intercepts the system back button and displays a confirmation dialog
 * when the user attempts to exit from the Home screen. For other screens, it performs
 * standard back navigation.
 *
 * The component uses [BackHandler] to intercept back button presses and shows a
 * [ConfirmationDialog] when appropriate.
 *
 * @param nav The [NavController] used for navigation operations and route detection.
 * @param handler The [PlatformApplication] handler for performing platform-specific operations
 * like finishing the application. Injected via Koin by default.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun BackPopup(
    nav: NavController,
    handler: PlatformApplication = koinInject(),
) {
    var showPopup by remember { mutableStateOf(false) }
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route ?: return

    BackHandler {
        showPopup = when (currentRoute) {
            Destination.Home.route -> true
            else -> false
        }
        if (!showPopup) {
            nav.popBackStack()
        }
    }

    ConfirmationDialog(
        modifier = Modifier
            .widthInDialog(),
        showDialog = showPopup,
        message = stringResource(Res.string.exit_app),
        onDismiss = {
            showPopup = false
        },
        onConfirm = {
            showPopup = false
            handler.finish()
        }
    )
}