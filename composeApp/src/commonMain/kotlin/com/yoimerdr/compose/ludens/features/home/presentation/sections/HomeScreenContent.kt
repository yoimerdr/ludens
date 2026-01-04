package com.yoimerdr.compose.ludens.features.home.presentation.sections

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.port.player.InputPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.MovementsPlayer
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.getEnabled
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.withPositionable
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.PositionableItemState
import com.yoimerdr.compose.ludens.features.home.presentation.components.ActionControls
import com.yoimerdr.compose.ludens.features.home.presentation.components.ConfigurationButton
import com.yoimerdr.compose.ludens.features.home.presentation.components.Joystick
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeEvent

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
 * @param movementsPlayer The movements player instance for handling joystick input
 * @param inputPlayer The input player instance for handling action button input
 * @param controls The control settings state containing enabled/disabled status and control item configurations
 * @param positions List of positionable item states defining the position (x, y coordinates) of each control element
 * @param onEvent Callback invoked when control events occur (joystick movements or button presses)
 * @param onConfiguration Callback invoked when the configuration button is clicked
 */
@Composable
fun BoxScope.HomeScreenContent(
    movementsPlayer: MovementsPlayer,
    inputPlayer: InputPlayer,
    controls: ControlSettingsState,
    positions: List<PositionableItemState>,
    onEvent: (HomeEvent) -> Unit,
    onConfiguration: () -> Unit,
) {
    val items = if (controls.enabled)
        controls.items
            .withPositionable(positions)
    else emptyList()

    ConfigurationButton(
        modifier = Modifier.align(Alignment.TopEnd),
        settings = items.getEnabled(ItemType.Settings)
            .firstOrNull(),
        onConfiguration = onConfiguration,
    )

    Joystick(
        joystick = items.getEnabled(ItemType.Joystick)
            .firstOrNull(),
        onEvent = onEvent,
        player = movementsPlayer,
        modifier = Modifier
            .align(Alignment.BottomStart)
    )

    ActionControls(
        items = items,
        player = inputPlayer,
        modifier = Modifier.align(Alignment.BottomEnd),
        onEvent = onEvent
    )
}