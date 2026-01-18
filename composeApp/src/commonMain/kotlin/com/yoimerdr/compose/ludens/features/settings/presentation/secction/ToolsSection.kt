package com.yoimerdr.compose.ludens.features.settings.presentation.secction

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ToolSettingsState
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionsContainer
import com.yoimerdr.compose.ludens.features.settings.presentation.components.ToolActionOption
import com.yoimerdr.compose.ludens.features.settings.presentation.components.ToolSwitchOption
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.SettingsViewModel
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.SingleTap
import com.yoimerdr.compose.ludens.ui.icons.outlined.SpeakerMute
import com.yoimerdr.compose.ludens.ui.icons.outlined.TopSpeed
import com.yoimerdr.compose.ludens.ui.icons.outlined.WindowDevTools
import com.yoimerdr.compose.ludens.ui.state.PluginState
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.stc_tools_move_controls
import ludens.composeapp.generated.resources.stc_tools_mute_audio
import ludens.composeapp.generated.resources.stc_tools_show_fps
import ludens.composeapp.generated.resources.stc_tools_use_webgl
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * The tools settings section displaying tool configuration options.
 *
 * @param features The current web features state.
 * @param settings The current tool settings state.
 * @param plugin The current plugin state.
 * @param onEvent Callback invoked when a settings event occurs.
 * @param modifier The modifier to be applied to the section container.
 * @param state The scroll state of the options list.
 */
@Composable
fun ToolsSettings(
    modifier: Modifier = Modifier,
    features: WebFeaturesState,
    settings: ToolSettingsState,
    plugin: PluginState,
    state: LazyListState = rememberLazyListState(),
    onEvent: (SettingsEvent) -> Unit,
) {

    OptionsContainer(
        modifier = modifier,
        state = state
    ) {
        item {
            ToolActionOption(
                onClick = {
                    onEvent(SettingsEvent.UpdateControlMovementMode(true))
                },
                title = stringResource(Res.string.stc_tools_move_controls),
                icon = LudensIcons.Default.SingleTap,
                iconDescription = "Move",
            )
        }

        item {
            ToolSwitchOption(
                title = stringResource(Res.string.stc_tools_mute_audio),
                icon = LudensIcons.Default.SpeakerMute,
                iconDescription = "Mute",
                enabled = features.supportsWebAudio,
                checked = settings.isMuted,
                onCheckedChange = {
                    onEvent(
                        SettingsEvent.UpdateAudioMuted(
                            it
                        )
                    )
                }
            )
        }

        item {
            ToolSwitchOption(
                title = stringResource(Res.string.stc_tools_show_fps),
                icon = LudensIcons.Default.TopSpeed,
                iconDescription = "FPS",
                enabled = plugin.isEnabled,
                checked = settings.showFPS,
                onCheckedChange = {
                    onEvent(SettingsEvent.UpdateShowFps(it))
                }
            )
        }

        item {
            ToolSwitchOption(
                title = stringResource(Res.string.stc_tools_use_webgl),
                icon = LudensIcons.Default.WindowDevTools,
                iconDescription = "WebGL",
                enabled = features.supportsWebGL,
                checked = settings.useWebGL,
                onCheckedChange = {
                    onEvent(SettingsEvent.UpdateUseWebGL(it))
                }
            )
        }
    }
}

/**
 * The tools settings section with view model integration.
 *
 * @param features The current web features state.
 * @param plugin The current plugin state.
 * @param viewModel The settings view model.
 * @param modifier The modifier to be applied to the section container.
 * @param state The scroll state of the options list.
 */
@Composable
fun ToolsSettings(
    features: WebFeaturesState,
    plugin: PluginState,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
    state: LazyListState = rememberLazyListState(),
) {
    val tools by viewModel.toolState.collectAsStateWithLifecycle()

    ToolsSettings(
        modifier = modifier,
        features = features,
        settings = tools,
        plugin = plugin,
        state = state,
        onEvent = viewModel::onEvent
    )
}