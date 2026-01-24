package com.yoimerdr.compose.ludens.features.settings.presentation.secction

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.InputKey
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.label
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlKeyItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlSettingsState
import com.yoimerdr.compose.ludens.features.settings.presentation.components.ControlButton
import com.yoimerdr.compose.ludens.features.settings.presentation.components.ControlOptionCard
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionName
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionsContainer
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.ControlSettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateControlAlpha
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateControlEnabled
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateControlKey
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateControlsAlpha
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateControlsEnabled
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.ControlsSettingsViewModel
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.Settings
import io.github.yoimerdr.compose.virtualjoystick.core.control.BackgroundType
import io.github.yoimerdr.compose.virtualjoystick.ui.view.JoystickBackground
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.stc_text_all_controls
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Displays a preview of a control with its alpha transparency applied.
 *
 * @param settings The current control settings state.
 * @param control The control item to preview.
 * @param keys The set of key control types.
 * @param editableKeys The set of available input keys for binding.
 * @param onEvent Callback invoked when a control key is updated.
 */
@Composable
private fun ControlAlphaPreview(
    settings: ControlSettingsState,
    control: ControlItemState,
    index: Int,
    keys: Set<ItemType>,
    editableKeys: Set<InputKey>,
    onEvent: (UpdateControlKey) -> Unit,
) {
    when (control.type) {
        in keys -> {
            if (control is ControlKeyItemState) {
                ControlButton(
                    control = control,
                    enabled = settings.enabled && control.enabled,
                    items = editableKeys
                ) {
                    onEvent(
                        UpdateControlKey(
                            index, it
                        )
                    )
                }
            }
        }

        ItemType.Joystick -> {
            JoystickBackground(
                modifier = Modifier.size(48.dp).alpha(control.alpha),
                type = BackgroundType.DpadModern
            )
        }

        ItemType.Settings -> {
            ControlButton(
                modifier = Modifier.alpha(control.alpha)
            ) {
                Icon(
                    LudensIcons.Default.Settings,
                    contentDescription = null,
                )
            }
        }

        else -> {}
    }
}

/**
 * The controls settings section displaying control configuration options.
 *
 * @param settings The current control settings state.
 * @param onEvent Callback invoked when a settings event occurs.
 * @param modifier The modifier to be applied to the section container.
 * @param state The scroll state of the options list.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlsSettingsSection(
    modifier: Modifier = Modifier,
    settings: ControlSettingsState,
    state: LazyListState = rememberLazyListState(),
    onEvent: (ControlSettingsEvent) -> Unit,
) {
    val keyControls = ItemType.keys
    val editableKeys = InputKey.controls

    OptionsContainer(
        modifier = modifier, state = state
    ) {
        if (settings.items.size > 1) {
            item {
                ControlOptionCard(
                    checked = settings.enabled,
                    enabledAlpha = settings.enabled,
                    onCheckedChange = {
                        onEvent(
                            UpdateControlsEnabled(
                                it
                            )
                        )
                    },
                    alpha = settings.alpha,
                    onAlphaChange = {
                        onEvent(
                            UpdateControlsAlpha(
                                it
                            )
                        )
                    },
                ) {
                    OptionName(
                        text = stringResource(Res.string.stc_text_all_controls)
                    )
                }
            }
        }
        itemsIndexed(settings.items) { index, control ->
            ControlOptionCard(
                useSwitchField = control.type != ItemType.Settings,
                enabled = if (control.type != ItemType.Settings) settings.enabled
                else true,
                enabledAlpha = if (control.type != ItemType.Settings) control.enabled && settings.enabled
                else true,
                checked = control.enabled,
                onCheckedChange = {
                    onEvent(
                        UpdateControlEnabled(
                            index, it
                        )
                    )
                },
                alpha = control.alpha,
                onAlphaChange = {
                    onEvent(
                        UpdateControlAlpha(
                            index, it
                        )
                    )
                },
                alphaSample = {
                    ControlAlphaPreview(
                        settings = settings,
                        control = control,
                        index = index,
                        keys = keyControls,
                        editableKeys = editableKeys,
                        onEvent = onEvent
                    )
                }) {
                OptionName(
                    text = control.label
                )
            }
        }
    }
}

/**
 * The controls settings section with view model integration.
 *
 * @param viewModel The settings view model.
 * @param modifier The modifier to be applied to the section container.
 * @param state The scroll state of the options list.
 */
@Composable
fun ControlsSettingsSection(
    modifier: Modifier = Modifier,
    viewModel: ControlsSettingsViewModel = koinViewModel(),
    state: LazyListState = rememberLazyListState(),
) {
    val controls by viewModel.state.collectAsStateWithLifecycle()

    ControlsSettingsSection(
        modifier = modifier,
        settings = controls,
        state = state,
        onEvent = viewModel::handle
    )
}