package com.yoimerdr.compose.ludens.core.infrastructure.adapter.player

import com.yoimerdr.compose.ludens.core.domain.model.key.InputKeyEvent
import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType
import com.yoimerdr.compose.ludens.core.domain.port.KeyEventEvaluator
import com.yoimerdr.compose.ludens.core.domain.port.player.InputPlayer
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

/**
 * Adapter implementation of [InputPlayer] using key event evaluation.
 *
 * Handles general input key events (Enter, Escape, Shift, Control, Alt, etc.)
 * by converting them key events and evaluating them through the key event evaluator.
 *
 * This class is registered as a Factory in the dependency injection container.
 *
 * @property evaluator The key event evaluator for processing input key events.
 */
@Factory
class InputPlayerAdapter(
    @InjectedParam
    val evaluator: KeyEventEvaluator,
) : InputPlayer {

    override fun onKeyEvent(
        key: InputKeyEvent,
        pressed: Boolean,
    ) {
        evaluator.evaluateKeyEvent(
            KeyEventType.Down, !pressed
        ) { type, i ->
            key.copy(type = type, timeout = i)
        }
    }

    override fun onKeyEvent(key: InputKeyEvent) {
        evaluator.evaluateKeyEvent(key)
    }
}