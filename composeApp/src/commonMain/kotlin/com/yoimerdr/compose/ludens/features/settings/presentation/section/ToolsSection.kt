package com.yoimerdr.compose.ludens.features.settings.presentation.section

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.yoimerdr.compose.ludens.app.navigation.Destination
import com.yoimerdr.compose.ludens.core.presentation.components.effects.FpsCounterVisible
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ToolSettingsState
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionsContainer
import com.yoimerdr.compose.ludens.features.settings.presentation.components.ToolActionOption
import com.yoimerdr.compose.ludens.features.settings.presentation.components.ToolSwitchOption
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.ToolSettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateShowFps
import com.yoimerdr.compose.ludens.features.settings.presentation.state.requests.RequestMute
import com.yoimerdr.compose.ludens.features.settings.presentation.state.requests.RequestWebGL
import com.yoimerdr.compose.ludens.features.settings.presentation.state.requests.ToolSectionRequest
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.ToolsSettingsViewModel
import com.yoimerdr.compose.ludens.ui.components.provider.CollectInteractionResult
import com.yoimerdr.compose.ludens.ui.components.provider.LocalInteractionManager
import com.yoimerdr.compose.ludens.ui.components.provider.LocalPlugin
import com.yoimerdr.compose.ludens.ui.components.provider.LocalWebFeatures
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.SingleTap
import com.yoimerdr.compose.ludens.ui.icons.outlined.SpeakerMute
import com.yoimerdr.compose.ludens.ui.icons.outlined.TopSpeed
import com.yoimerdr.compose.ludens.ui.icons.outlined.WindowDevTools
import kotlinx.coroutines.launch
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.stc_tools_move_controls
import ludens.composeapp.generated.resources.stc_tools_mute_audio
import ludens.composeapp.generated.resources.stc_tools_show_fps
import ludens.composeapp.generated.resources.stc_tools_use_webgl
import org.jetbrains.compose.resources.stringResource

/**
 * The tools settings section displaying tool configuration options.
 *
 * @param settings The current tool settings state.
 * @param onEvent Callback invoked when a tool settings event occurs.
 * @param onNavigate Callback invoked when a navigation event occurs.
 * @param modifier The modifier to be applied to the section container.
 * @param state The scroll state of the options list.
 */
@Composable
fun ToolsSettingsSection(
    settings: ToolSettingsState,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    onNavigate: (Destination) -> Unit,
    onRequest: (ToolSectionRequest) -> Unit,
    onEvent: (ToolSettingsEvent) -> Unit,
) {
    val features = LocalWebFeatures.current
    val plugin = LocalPlugin.current

    FpsCounterVisible {
        onEvent(UpdateShowFps(it))
    }

    OptionsContainer(
        modifier = modifier,
        state = state
    ) {
        item {
            ToolActionOption(
                onClick = {
                    onNavigate(Destination.SettingsPositions)
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
                    onRequest(RequestMute(it))
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
                    onEvent(UpdateShowFps(it))
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
                    onRequest(RequestWebGL(it))
                }
            )
        }
    }
}

/**
 * The tools settings section with view model integration.
 *
 * @param viewModel The tools section view model.
 * @param onNavigate Callback invoked when a navigation event occurs.
 * @param modifier The modifier to be applied to the section container.
 * @param state The scroll state of the options list.
 */
@Composable
fun ToolsSettingsSection(
    modifier: Modifier = Modifier,
    viewModel: ToolsSettingsViewModel,
    state: LazyListState = rememberLazyListState(),
    onNavigate: (Destination) -> Unit,
) {
    val interactionManager = LocalInteractionManager.current
    val tools by viewModel.state.collectAsStateWithLifecycle()

    CollectInteractionResult {
        if (it.request is ToolSectionRequest)
            viewModel.resolve(it.request)
    }

    ToolsSettingsSection(
        modifier = modifier,
        settings = tools,
        state = state,
        onEvent = viewModel::handle,
        onNavigate = onNavigate,
        onRequest = {
            viewModel.viewModelScope.launch {
                interactionManager.request(it)
            }
        }
    )
}