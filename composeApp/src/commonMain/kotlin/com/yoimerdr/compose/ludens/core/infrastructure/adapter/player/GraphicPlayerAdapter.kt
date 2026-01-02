package com.yoimerdr.compose.ludens.core.infrastructure.adapter.player

import com.yoimerdr.compose.ludens.core.domain.model.key.GraphicsKeyEvent
import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType
import com.yoimerdr.compose.ludens.core.domain.port.KeyEventEvaluator
import com.yoimerdr.compose.ludens.core.domain.port.player.GraphicPlayer
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

/**
 * Adapter implementation of [GraphicPlayer] using key event evaluation.
 *
 * Handles graphics-related key events (F2, F3, F4) by converting them to
 * key events and evaluating them through the key event evaluator.
 *
 * This class is registered as a Factory in the dependency injection container.
 *
 * @property evaluator The key event evaluator for processing graphics key events.
 */
@Factory
class GraphicPlayerAdapter(
    @InjectedParam
    val evaluator: KeyEventEvaluator,
) : GraphicPlayer {

    override fun onKeyEvent(key: GraphicsKeyEvent, pressed: Boolean) {
        evaluator.evaluateKeyEvent(KeyEventType.Down, false) { type, it ->
            key.copy(type = type, timeout = it)
        }
    }
}