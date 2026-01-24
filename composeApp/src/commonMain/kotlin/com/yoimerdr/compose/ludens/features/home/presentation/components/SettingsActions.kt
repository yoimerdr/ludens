package com.yoimerdr.compose.ludens.features.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.IntOffset
import com.yoimerdr.compose.ludens.app.theme.outlineDark
import com.yoimerdr.compose.ludens.core.domain.factory.SettingsFactory.settings
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionType
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableType
import com.yoimerdr.compose.ludens.core.domain.value.Alpha
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ActionItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ActionSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlActionItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.PositionableItemState
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
import kotlinx.collections.immutable.toPersistentList
import kotlin.math.roundToInt

/**
 * Displays the configuration/settings button.
 *
 * The button exact position and appearance can be customized through the [settings] parameter.
 * If no settings are provided, default values are used with medium alpha transparency.
 *
 * @param onConfiguration Callback invoked when the configuration button is clicked
 * @param settings Optional positionable control item configuration that defines the button's
 * position (x, y coordinates) and appearance (enabled state, alpha transparency).
 * If null, defaults to offset position (0, 0) with medium alpha.
 *
 * @see Alpha.Medium
 */
@Composable
fun SettingsActions(
    modifier: Modifier = Modifier,
    onActionClick: (ActionItemState) -> Unit,
    onConfiguration: () -> Unit,
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
                            Icon(
                                imageVector = when (it.type) {
                                    ActionType.Settings -> LudensIcons.Default.Settings
                                    ActionType.ToggleFPS -> LudensIcons.Default.TopSpeed
                                    ActionType.ToggleMute -> LudensIcons.Default.SpeakerMute
                                    ActionType.ToggleWebGL -> LudensIcons.Default.WindowDevTools
                                    ActionType.ToggleControls -> LudensIcons.Default.Games
                                },
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SettingsActions(
    modifier: Modifier = Modifier,
    onConfiguration: () -> Unit,
    actions: ActionSettingsState,
    control: ControlItemState?,
    position: PositionableItemState?,
    onActionClick: (ActionItemState) -> Unit,
) {
    SettingsActions(
        modifier = modifier,
        onConfiguration = onConfiguration,
        actions = actions.items
            .toPersistentList()
            .mutate {
                it.groupBy { item -> item.order }
            },
        control = control,
        position = position,
        onActionClick = onActionClick
    )
}