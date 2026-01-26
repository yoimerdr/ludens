package com.yoimerdr.compose.ludens.core.infrastructure.adapter.player

import com.yoimerdr.compose.ludens.core.domain.port.ScriptEvaluator
import com.yoimerdr.compose.ludens.core.domain.port.evaluatingScript
import com.yoimerdr.compose.ludens.core.domain.port.player.AudioPlayer
import com.yoimerdr.compose.ludens.core.domain.value.Volume
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

/**
 * Adapter implementation of [AudioPlayer].
 *
 * Controls game audio through script execution in the `YDP_Ludens`.audio API.
 * Provides mute/unmute functionality and volume control.
 *
 * This class is registered as a Factory in the dependency injection container.
 *
 * @property evaluator The script evaluator for executing audio control commands.
 */
@Factory
class AudioPlayerAdapter(
    @InjectedParam
    val evaluator: ScriptEvaluator,
) : AudioPlayer {
    override fun mute() {
        evaluator.evaluateScript("YDP_Ludens.audio.mute();")
    }

    override fun unmute() {
        evaluator.evaluateScript("YDP_Ludens.audio.unmute();")
    }

    override fun volume(level: Volume) {
        evaluator.evaluateScript("YDP_Ludens.audio.setVolume(${level.value});")
    }

    override suspend fun isMuted(): Boolean {
        return evaluator.evaluatingScript("YDP_Ludens.audio.isMuted")
            .toBoolean()
    }

    override fun isMuted(isMuted: (Boolean) -> Unit) {
        evaluator.evaluateScript("YDP_Ludens.audio.isMuted") {
            isMuted(it.toBoolean())
        }
    }
}