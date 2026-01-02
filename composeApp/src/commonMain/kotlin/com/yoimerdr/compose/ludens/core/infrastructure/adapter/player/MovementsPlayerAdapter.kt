package com.yoimerdr.compose.ludens.core.infrastructure.adapter.player

import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType
import com.yoimerdr.compose.ludens.core.domain.model.key.MovementKeyEvent
import com.yoimerdr.compose.ludens.core.domain.port.KeyEventEvaluator
import com.yoimerdr.compose.ludens.core.domain.port.player.MovementsPlayer
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.InputKey
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

    /**
     * Prepares a movement key event for evaluation.
     *
     * @param type The type of key event (Down or Up).
     * @param key The movement input key.
     * @param timeout Whether to apply a timeout to the event.
     */
    private fun prepareKeyEvent(
        type: KeyEventType,
        key: InputKey,
        timeout: Boolean = false,
    ) {
        evaluator.prepareKeyEvent(type, timeout) { type, timeout ->
            MovementKeyEvent(
                type = type,
                code = key.code,
                timeout = timeout
            )
        }
    }

    /**
     * Evaluates multiple movement key events, resetting conflicting directions.
     *
     * Resets all movement keys not in the provided set to the opposite state,
     * then applies the requested keys with the specified type.
     *
     * @param type The type of key event (Down or Up).
     * @param keys Set of movement keys to activate.
     * @param timeout Whether to apply a timeout to the events.
     */
    private fun evaluateKeyEvent(
        type: KeyEventType,
        keys: Set<InputKey>,
        timeout: Boolean = false,
    ) {
        val resetType = when (type) {
            KeyEventType.Down -> KeyEventType.Up
            KeyEventType.Up -> KeyEventType.Down
        }
        (InputKey.movements - keys).forEach {
            prepareKeyEvent(resetType, it)
        }

        keys.forEach {
            prepareKeyEvent(type, it, timeout)
        }

        evaluator.evaluateKeyEventScript()
    }

    private fun evaluateKeyEvent(
        type: KeyEventType,
        key: InputKey,
        timeout: Boolean = false,
    ) = evaluateKeyEvent(type, setOf(key), timeout)

    override fun up(pressed: Boolean) {
        evaluateKeyEvent(
            KeyEventType.Down,
            InputKey.Up,
            !pressed
        )
    }

    override fun down(pressed: Boolean) {
        evaluateKeyEvent(
            KeyEventType.Down,
            InputKey.Down,
            !pressed
        )
    }

    override fun left(pressed: Boolean) {
        evaluateKeyEvent(
            KeyEventType.Down,
            InputKey.Left,
            !pressed
        )
    }

    override fun right(pressed: Boolean) {
        evaluateKeyEvent(
            KeyEventType.Down,
            InputKey.Right,
            !pressed
        )
    }

    override fun upLeft(pressed: Boolean) {
        evaluateKeyEvent(
            KeyEventType.Down,
            InputKey.upLeft,
            !pressed
        )
    }

    override fun upRight(pressed: Boolean) {
        evaluateKeyEvent(
            KeyEventType.Down,
            InputKey.upRight,
            !pressed
        )
    }

    override fun downLeft(pressed: Boolean) {
        evaluateKeyEvent(
            KeyEventType.Down,
            InputKey.downLeft,
            !pressed
        )
    }

    override fun downRight(pressed: Boolean) {
        evaluateKeyEvent(
            KeyEventType.Down,
            InputKey.downRight,
            !pressed
        )
    }

    override fun none() {
        evaluateKeyEvent(
            KeyEventType.Up,
            InputKey.movements,
        )
    }
}