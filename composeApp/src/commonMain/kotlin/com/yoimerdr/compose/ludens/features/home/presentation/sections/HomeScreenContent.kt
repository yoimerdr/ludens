package com.yoimerdr.compose.ludens.features.home.presentation.sections

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableType
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.getEnabled
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.PositionableItemState
import com.yoimerdr.compose.ludens.features.home.presentation.components.SettingsActions
import com.yoimerdr.compose.ludens.features.home.presentation.components.Joystick
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeEvent
import com.yoimerdr.compose.ludens.features.home.presentation.viewmodel.HomeViewModel

/**
 * Displays the main content of the home screen including game controls.
 *
 * This composable renders a complete set of game control UI elements within a [BoxScope]:
 * - Configuration button (top-end) for accessing settings
 * - Virtual joystick (bottom-start) for player movement
 * - Action control buttons (bottom-end) for game actions
 *
 * Control visibility and positioning are determined by the [controls] settings and
 * [positions] configuration. Only enabled controls are displayed. If controls are
 * globally disabled (controls.enabled is false), only the configuration button
 * remains visible.
 *
 * @param controls The control settings state containing enabled/disabled status and control item configurations
 * @param positions List of positionable item states defining the position (x, y coordinates) of each control element
 * @param onEvent Callback invoked when control events occur (joystick movements or button presses)
 * @param showControls Whether to show the game controls (joystick, buttons)
 * @param onConfiguration Callback invoked when the configuration button is clicked
 */
@Composable
fun BoxScope.HomeScreenContent(
    controls: ControlSettingsState,
    positions: List<PositionableItemState>,
    onEvent: (HomeEvent) -> Unit,
    showControls: Boolean = true,
    onConfiguration: () -> Unit,
) {
    if (!showControls)
        return

    var showSettings = false

    positions.forEach {
        when (it.type) {
            PositionableType.Actions -> {
                showSettings = true
                SettingsActions(
                    modifier = Modifier.align(Alignment.TopEnd),
                    position = it,
                    control = controls.items.getEnabled(ItemType.Actions)
                        .firstOrNull(),
                    onConfiguration = onConfiguration,
                )
            }

            PositionableType.Joystick -> {
                Joystick(
                    joystick = controls.items.getEnabled(ItemType.Joystick)
                        .firstOrNull(),
                    onEvent = onEvent,
                    position = it,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                )
            }

            PositionableType.Keys -> {
                KeyControls(
                    items = controls.items.getEnabled(ItemType.keys),
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onEvent = onEvent,
                    position = it,
                )
            }

            else -> {

            }
        }
    }

    if (!showSettings) {
        SettingsActions(
            modifier = Modifier.align(Alignment.TopEnd),
            position = null,
            control = null,
            onConfiguration = onConfiguration,
            onActionClick = {

            }
        )
    }

}

/**
 * HomeScreenContent composable that connects to the HomeViewModel.
 *
 * This overload receives the viewModel instance to ensure consistency across the home section.
 * It collects the control state and delegates to the stateless version of HomeScreenContent.
 *
 * @param viewModel The home view model instance. Must be provided from parent to avoid
 * creating multiple ViewModel instances which would cause state inconsistency.
 * @param showControls Whether to show the game controls (joystick, buttons)
 * @param onConfiguration Callback invoked when the configuration button is clicked
 */
@Composable
fun BoxScope.HomeScreenContent(
    viewModel: HomeViewModel,
    showControls: Boolean = true,
    onConfiguration: () -> Unit,
) {
    val controls by viewModel.controlState.collectAsStateWithLifecycle()

    HomeScreenContent(
        controls = controls,
        positions = controls.positions,
        onEvent = viewModel::handle,
        onConfiguration = onConfiguration,
        showControls = showControls
    )
}