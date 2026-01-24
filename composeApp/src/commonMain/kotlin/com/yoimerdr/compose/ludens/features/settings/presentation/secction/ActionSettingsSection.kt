package com.yoimerdr.compose.ludens.features.settings.presentation.secction

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionType
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.label
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ActionItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ActionSettingsState
import com.yoimerdr.compose.ludens.features.settings.presentation.components.ControlOptionCard
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionName
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionsContainer
import com.yoimerdr.compose.ludens.features.settings.presentation.components.ToolOption
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SwapActionOrder
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateActionEnabled
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateActionsEnabled
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.ActionSettingsViewModel
import com.yoimerdr.compose.ludens.ui.components.fields.SwitchField
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.Drag
import com.yoimerdr.compose.ludens.ui.icons.outlined.Games
import com.yoimerdr.compose.ludens.ui.icons.outlined.Settings
import com.yoimerdr.compose.ludens.ui.icons.outlined.SpeakerMute
import com.yoimerdr.compose.ludens.ui.icons.outlined.TopSpeed
import com.yoimerdr.compose.ludens.ui.icons.outlined.WindowDevTools
import org.koin.compose.viewmodel.koinViewModel
import sh.calvin.reorderable.ReorderableCollectionItemScope
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

/**
 * A reorderable action setting option card.
 *
 * This composable displays a single action item with its icon, label, checkbox, and drag handle.
 * The item can be reordered within a list and enabled/disabled via a checkbox. The Settings action
 * is always enabled and cannot be disabled, but can be reordered. Visual feedback is provided
 * through alpha transparency for disabled items.
 *
 * @param item The action item state to display.
 * @param enabled Whether the quick actions feature is enabled globally. Controls the checkbox and drag handle.
 * @param onCheckedChange Callback invoked when the checkbox state changes.
 */
@Composable
private fun ReorderableCollectionItemScope.ActionSettingOption(
    item: ActionItemState,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
) {
    ToolOption(
        prefix = {
            val color = LocalContentColor.current
            CompositionLocalProvider(
                LocalContentColor provides if (item.enabled) color
                else color.copy(alpha = 0.38f)
            ) {
                Icon(
                    imageVector = when (item.type) {
                        ActionType.Settings -> LudensIcons.Default.Settings
                        ActionType.ToggleFPS -> LudensIcons.Default.TopSpeed
                        ActionType.ToggleMute -> LudensIcons.Default.SpeakerMute
                        ActionType.ToggleWebGL -> LudensIcons.Default.WindowDevTools
                        ActionType.ToggleControls -> LudensIcons.Default.Games
                    },
                    contentDescription = null,
                )

                OptionName(
                    text = item.type.label
                )
            }
        },
    ) {
        Row {
            val isSettings = item.type == ActionType.Settings
            val canMove = if (isSettings) enabled
            else enabled && item.enabled
            Checkbox(
                checked = item.enabled,
                enabled = if (isSettings) false
                else enabled,
                onCheckedChange = onCheckedChange,
            )

            IconButton(
                modifier = Modifier.draggableHandle(
                    enabled = canMove
                ),
                enabled = canMove,
                onClick = {},
            ) {
                Icon(
                    imageVector = LudensIcons.Default.Drag, contentDescription = "Move"
                )
            }
        }
    }
}

/**
 * The action settings section displaying quick actions configuration.
 *
 * This composable displays the quick actions settings, allowing users to:
 * - Enable/disable the quick actions feature globally
 * - Enable/disable individual action items (except Settings, which is always enabled)
 * - Reorder action items by dragging them
 *
 * The section includes a master switch to control the entire quick actions feature,
 * followed by a reorderable list of action items. Each action item shows its icon,
 * label, a checkbox to enable/disable it, and a drag handle for reordering.
 *
 * @param modifier The modifier to be applied to the section container.
 * @param settings The current action settings state containing enabled state and action items.
 * @param state The scroll state of the options list.
 * @param onEvent Callback invoked when a settings event occurs.
 */
@Composable
fun ActionSettingsSection(
    modifier: Modifier = Modifier,
    settings: ActionSettingsState,
    state: LazyListState = rememberLazyListState(),
    onEvent: (SettingsEvent) -> Unit,
) {
    val reorderable = rememberReorderableLazyListState(state) { from, to ->
        onEvent(SwapActionOrder(from.index - 1 to to.index - 1))
    }

    OptionsContainer(
        modifier = modifier, state = state
    ) {
        item {
            ControlOptionCard {
                SwitchField(
                    Modifier.fillMaxWidth(),
                    checked = settings.enabled,
                    onCheckedChange = {
                        onEvent(UpdateActionsEnabled(it))
                    },
                ) {
                    OptionName(
                        text = "Show as quick actions"
                    )
                }
            }
        }

        itemsIndexed(settings.items, key = { index, it -> it.type }) { index, item ->
            ReorderableItem(
                state = reorderable, key = item.type, enabled = item.enabled
            ) {
                ActionSettingOption(
                    item = item,
                    enabled = settings.enabled,
                ) {
                    onEvent(UpdateActionEnabled(index, it))
                }
            }
        }
    }
}

/**
 * The action settings section with view model integration.
 *
 * This is a convenience overload that automatically retrieves the action settings state
 * from the provided view model and delegates to the main [ActionSettingsSection] composable.
 *
 * @param modifier The modifier to be applied to the section container.
 * @param state The scroll state of the options list.
 * @param viewModel The settings view model providing the action state.
 */
@Composable
fun ActionSettingsSection(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    viewModel: ActionSettingsViewModel = koinViewModel(),
) {
    val actions by viewModel.state.collectAsStateWithLifecycle()

    ActionSettingsSection(
        modifier = modifier, state = state, settings = actions, onEvent = viewModel::handle
    )
}
