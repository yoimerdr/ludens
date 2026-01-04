package com.yoimerdr.compose.ludens.core.infrastructure.adapter.player

import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType
import com.yoimerdr.compose.ludens.core.domain.model.key.MovementKeyEvent
import com.yoimerdr.compose.ludens.core.domain.port.KeyEventEvaluator
import com.yoimerdr.compose.ludens.core.domain.port.player.MovementsPlayer
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.InputKey
import com.yoimerdr.compose.ludens.core.infrastructure.extension.key.toMovementEvent
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

/**
 * Adapter implementation of [MovementsPlayer] using key event evaluation.
 *
 * Handles directional movement key events (Up, Down, Left, Right and diagonal combinations)
 * by converting them to key events and evaluating them through the key event evaluator.
 * Ensures that conflicting movement directions are properly reset.
 *
 * This class is registered as a Factory in the dependency injection container.
 *
 * @property evaluator The key event evaluator for processing movement key events.
 */
@Factory
class MovementsPlayerAdapter(
    @InjectedParam
    val evaluator: KeyEventEvaluator,
) : MovementsPlayer {

    override fun onKeyEvent(
        key: MovementKeyEvent,
        pressed: Boolean,
    ) {
        val input = InputKey.from(key.code) ?: return

        (InputKey.movements - input).forEach {
            evaluator.prepareKeyEvent(it.toMovementEvent(KeyEventType.Up))
        }

        evaluator.prepareKeyEvent(KeyEventType.Down, !pressed) { type, it ->
            key.copy(type = type, timeout = it)
        }

        evaluator.evaluateKeyEventScript()
    }

    override fun onKeyEvent(
        key: MovementKeyEvent,
        vararg keys: MovementKeyEvent,
    ) {
        val keys = (setOf(key) + keys)
            .filter { InputKey.from(it.code) != null }

        if (keys.isEmpty())
            return

        keys.forEach {
            evaluator.prepareKeyEvent(it)
        }

        evaluator.evaluateKeyEventScript()
    }
}