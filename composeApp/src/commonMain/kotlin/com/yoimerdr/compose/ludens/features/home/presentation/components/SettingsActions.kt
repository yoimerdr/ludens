package com.yoimerdr.compose.ludens.features.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.IntOffset
import com.yoimerdr.compose.ludens.app.theme.outlineDark
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionType
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableType
import com.yoimerdr.compose.ludens.core.domain.value.Alpha
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ActionItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ActionSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlActionItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.PositionableItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ToolSettingsState
import com.yoimerdr.compose.ludens.ui.components.buttons.OutlinedIconButton
import com.yoimerdr.compose.ludens.ui.components.layout.Card
import com.yoimerdr.compose.ludens.ui.components.layout.FloatingDock
import com.yoimerdr.compose.ludens.ui.components.layout.FloatingDockHandleButton
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.Games
import com.yoimerdr.compose.ludens.ui.icons.outlined.Settings
import com.yoimerdr.compose.ludens.ui.icons.outlined.SpeakerMute
import com.yoimerdr.compose.ludens.ui.icons.outlined.TopSpeed
import com.yoimerdr.compose.ludens.ui.icons.outlined.WindowDevTools
import com.yoimerdr.compose.ludens.ui.state.layout.DockMode
import com.yoimerdr.compose.ludens.ui.state.layout.rememberDockState
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlin.math.roundToInt

/**
 * Displays the configuration or quick actions settings button according to the [actions].
 *
 * If [actions] is empty, it displays a single configuration button. Otherwise, it displays a
 * floating dock with the actions.  
 *
 * @param modifier The modifier to be applied to the button container
 * @param onActionClick Callback invoked when a specific action item is clicked
 * @param onConfiguration Callback invoked when the configuration button is clicked
 * @param toolSettings The current tool settings state (mute, FPS, etc.)
 * @param controlSettings The current control settings state
 * @param actions List of available action items to display in the dock
 * @param control Optional control configuration that defines appearance (alpha transparency).
 * @param position Optional position configuration that defines the button's coordinates (x, y).
 *
 * @see Alpha.Medium
 */
@Composable
fun SettingsActions(
    modifier: Modifier = Modifier,
    onActionClick: (ActionItemState) -> Unit,
    onConfiguration: () -> Unit,
    toolSettings: ToolSettingsState,
    controlSettings: ControlSettingsState,
    actions: List<ActionItemState> = emptyList(),
    control: ControlItemState?,
    position: PositionableItemState?,
) {
    val control = control ?: ControlActionItemState(
        enabled = true,
        type = ItemType.Actions,
        alpha = Alpha.Medium.value
    )

    val position = position ?: PositionableItemState(
        type = PositionableType.Actions,
        x = 0f,
        y = 0f,
    )

    val offset = modifier
        .offset {
            IntOffset(
                x = position.x.roundToInt(),
                y = position.y.roundToInt(),
            )
        }

    if (actions.isEmpty()) {
        OutlinedIconButton(
            border = null,
            onClick = onConfiguration,
            modifier = offset
                .alpha(control.alpha)
        ) {
            Icon(
                LudensIcons.Default.Settings,
                contentDescription = "Settings",
            )
        }
    } else {
        FloatingDock(
            modifier = offset,
            dockState = rememberDockState(initialState = DockMode.Idle),
            handleButton = {
                FloatingDockHandleButton(
                    state = it,
                    tint = outlineDark.copy(
                        alpha = control.alpha
                    ),
                )
            },
        ) {
            Card(
                padding = PaddingValues.Zero,
            ) {
                Column {
                    actions.forEach {
                        IconButton(
                            shape = MaterialTheme.shapes.small,
                            onClick = {
                                onActionClick(it)
                            }
                        ) {
                            val isActive = when (it.type) {
                                ActionType.ToggleMute -> toolSettings.isMuted
                                ActionType.ToggleWebGL -> toolSettings.useWebGL
                                ActionType.ToggleFPS -> toolSettings.showFPS
                                ActionType.ToggleControls -> controlSettings.enabled
                                else -> false
                            }
                            Icon(
                                imageVector = when (it.type) {
                                    ActionType.Settings -> LudensIcons.Default.Settings
                                    ActionType.ToggleFPS -> LudensIcons.Default.TopSpeed
                                    ActionType.ToggleMute -> LudensIcons.Default.SpeakerMute
                                    ActionType.ToggleWebGL -> LudensIcons.Default.WindowDevTools
                                    ActionType.ToggleControls -> LudensIcons.Default.Games
                                },
                                contentDescription = null,
                                tint = if (isActive)
                                    MaterialTheme.colorScheme.primary
                                else LocalContentColor.current
                            )
                        }
                    }
                }
            }
        }
    }
}


/**
 * Displays the configuration/settings button using the provided action settings state.
 *
 * This overload simplifies the usage by taking [ActionSettingsState] and extracting
 * the enabled items to pass to the main [SettingsActions] composable. It handles
 * grouping and ordering of action items automatically.
 *
 * @param modifier The modifier to be applied to the button container
 * @param onConfiguration Callback invoked when the configuration button is clicked
 * @param toolSettings The current tool settings state (mute, FPS, etc.)
 * @param controlSettings The current control settings state (joystick, buttons enabled)
 * @param actions The action settings state containing the list of available actions
 * @param control Optional control overrides for the settings button itself
 * @param position Optional position overrides for the settings button
 * @param onActionClick Callback invoked when a specific action item is clicked
 */
@Composable
fun SettingsActions(
    modifier: Modifier = Modifier,
    onConfiguration: () -> Unit,
    toolSettings: ToolSettingsState,
    controlSettings: ControlSettingsState,
    actions: ActionSettingsState,
    control: ControlItemState?,
    position: PositionableItemState?,
    onActionClick: (ActionItemState) -> Unit,
) {
    SettingsActions(
        modifier = modifier,
        onConfiguration = onConfiguration,
        actions = if (actions.enabled)
            actions.items
                .toPersistentList()
                .mutate {
                    it.groupBy { item -> item.order }
                }
        else persistentListOf(),
        toolSettings = toolSettings,
        controlSettings = controlSettings,
        control = control,
        position = position,
        onActionClick = onActionClick
    )
}