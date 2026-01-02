package com.yoimerdr.compose.ludens.core.infrastructure.adapter.player

import com.yoimerdr.compose.ludens.core.domain.port.ScriptEvaluator
import com.yoimerdr.compose.ludens.core.domain.port.player.FPSPlayer
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

/**
 * Adapter implementation of [FPSPlayer].
 *
 * Controls the FPS (Frames Per Second) display through script execution
 * in the `YDP_Ludens`.fps API. Allows showing, hiding, and toggling the FPS counter.
 *
 * This class is registered as a Factory in the dependency injection container.
 *
 * @property evaluator The script evaluator for executing FPS control commands.
 */
@Factory
class FPSPlayerAdapter(
    @InjectedParam
    val evaluator: ScriptEvaluator,
) : FPSPlayer {
    override fun show() {
        evaluator.evaluateScript("YDP_Ludens.fps.show();")
    }

    override fun hide() {
        evaluator.evaluateScript("YDP_Ludens.fps.hide();")
    }

    override fun toggle() {
        evaluator.evaluateScript("YDP_Ludens.fps.toggle();")
    }

    override val isVisible: Boolean
        get() {
            var result = false
            evaluator.evaluateScript("YDP_Ludens.fps.isVisible") {
                result = it.toBoolean()
            }
            return result
        }
}