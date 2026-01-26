package com.yoimerdr.compose.ludens.core.presentation.components.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.yoimerdr.compose.ludens.app.ui.providers.LocalAudioPlayer
import com.yoimerdr.compose.ludens.app.ui.providers.LocalFPSPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.AudioPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.FPSPlayer
import com.yoimerdr.compose.ludens.ui.components.provider.LocalPlugin
import com.yoimerdr.compose.ludens.ui.state.PluginState
import com.yoimerdr.compose.ludens.ui.state.isAvailable

/**
 * Checks the visibility state of the FPS counter from the [player].
 *
 * When the [plugin] is available, it requests the current visibility state from the FPS player
 * and invokes [isVisible] with the result. This check is performed whenever the [player], [plugin],
 * or its availability changes.
 *
 * @param player The FPS player interface to collect events from.
 * @param plugin The current plugin state.
 * @param isVisible Callback invoked with the current visibility state.
 */
@Composable
fun FpsCounterVisible(
    player: FPSPlayer = LocalFPSPlayer.current,
    plugin: PluginState = LocalPlugin.current,
    isVisible: (Boolean) -> Unit,
) {
    LaunchedEffect(player, plugin, plugin.isAvailable) {
        if (plugin.isAvailable)
            player.isVisible(isVisible)
    }
}

/**
 * Checks the mute state of the audio from the [player].
 *
 * When the [plugin] is available, it requests the current mute state from the audio player
 * and invokes [isMuted] with the result. This check is performed whenever the [player], [plugin],
 * or its availability changes.
 *
 * @param player The Audio player interface to collect events from.
 * @param plugin The current plugin state.
 * @param isMuted Callback invoked with the current mute state.
 */
@Composable
fun AudioMuted(
    player: AudioPlayer = LocalAudioPlayer.current,
    plugin: PluginState = LocalPlugin.current,
    isMuted: (Boolean) -> Unit,
) {
    LaunchedEffect(player, plugin, plugin.isAvailable) {
        if (plugin.isAvailable)
            player.isMuted(isMuted)
    }
}
