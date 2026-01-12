package com.yoimerdr.compose.ludens.ui.state.effects

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

/**
 * Default values and utility functions for [FloatEffect] and [FloatEffectEvent].
 *
 * Provides pre-configured flash events and helper methods for creating and comparing float effect events.
 */
object FloatEffectDefaults {
    private var _cachedFlashEvent: DualFloatEffectEvent? = null

    internal val defaultFlashEvent: DualFloatEffectEvent
        get() {
            return _cachedFlashEvent ?: DualFloatEffectEvent(
                value = 0.8f,
                exitValue = 0f,
                animationSpec = tween(
                    durationMillis = 80,
                    easing = LinearOutSlowInEasing
                ),
                exitAnimationSpec = tween(
                    durationMillis = 240,
                    easing = LinearEasing
                ),
                delay = 0L,
            ).also {
                _cachedFlashEvent = it
            }
        }

    /**
     * Creates a default flash effect event with predefined animation values.
     *
     * @return A pre-configured [DualFloatEffectEvent] for flash animations.
     */
    fun flashEvent(): FloatEffectEvent = defaultFlashEvent

    /**
     * Creates a customizable flash effect event.
     *
     * @param enterValue The target value for the enter animation.
     * @param enterSpec The animation specification for the enter animation.
     * @param exitValue The target value for the exit animation.
     * @param exitSpec The animation specification for the exit animation.
     * @param delay The delay in milliseconds before triggering onComplete callback.
     * @param onStart Callback invoked when the animation starts.
     * @param onComplete Callback invoked when the animation completes.
     */
    fun flashEvent(
        enterValue: Float? = null,
        enterSpec: AnimationSpec<Float>? = null,
        exitValue: Float? = null,
        exitSpec: AnimationSpec<Float>? = null,
        delay: Long? = null,
        onStart: OnEffect = null,
        onComplete: OnEffect = null,
    ): FloatEffectEvent {
        return defaultFlashEvent.let {
            it.copy(
                value = enterValue ?: it.value,
                spec = enterSpec ?: it.animationSpec,
                exitValue = exitValue ?: it.exitValue,
                exitAnimationSpec = exitSpec ?: it.exitAnimationSpec,
                delay = delay ?: it.delay,
                onStart = onStart ?: it.onStart,
                onComplete = onComplete ?: it.onComplete,
            )
        }
    }

    fun event(
        value: Float,
        animationSpec: AnimationSpec<Float>,
        delay: Long? = null,
        onStart: OnEffect = null,
        onComplete: OnEffect = null,
    ): FloatEffectEvent {
        return SingleFloatEffectEvent(
            value = value,
            animationSpec = animationSpec,
            delay = delay ?: 0L,
            onStart = onStart,
            onComplete = onComplete,
        )
    }

    /**
     * Compares two [FloatEffectEvent] instances for equality.
     *
     * Two events are considered equal if they have the same value and animation specification.
     *
     * @param event The event to compare.
     * @param other The object to compare with.
     * @see EffectEventDefaults.equals
     */
    fun equals(event: FloatEffectEvent, other: Any?): Boolean {
        if (event === other) return true
        if (other !is FloatEffectEvent) return false

        if (event.value != other.value) return false
        if (event.animationSpec != other.animationSpec) return false

        return true
    }

    /**
     * Generates a hash code for a [FloatEffectEvent].
     *
     * @param event The event to generate a hash code for.
     * @see EffectEventDefaults.hashCode
     */
    fun hashCode(event: FloatEffectEvent): Int {
        var result = event.value.hashCode()
        result = 31 * result + event.animationSpec.hashCode()
        result = 31 * result + EffectEventDefaults.hashCode(event)
        return result
    }
}

/**
 * Represents an event that triggers a float-based animation effect.
 *
 * This interface defines the contract for events that animate a float value with a specific animation specification.
 */
interface FloatEffectEvent : EffectEvent {
    /**
     * The target float value to animate to.
     */
    val value: Float

    /**
     * The animation specification that controls how the value animates.
     */
    val animationSpec: AnimationSpec<Float>

    /**
     * Creates a copy of this effect event with modified properties.
     *
     * @param value The target float value.
     * @param animationSpec The animation specification.
     * @param delay The delay before triggering callbacks.
     * @param onStart Callback invoked when animation starts.
     * @param onComplete Callback invoked when animation completes.
     */
    fun copy(
        value: Float = this.value,
        animationSpec: AnimationSpec<Float> = this.animationSpec,
        delay: Long = this.delay,
        onStart: OnEffect = this.onStart,
        onComplete: OnEffect = this.onComplete,
    ): FloatEffectEvent

    override fun copy(delay: Long, onStart: OnEffect, onComplete: OnEffect): FloatEffectEvent
}

/**
 * An effect that manages float-based animations.
 */
interface FloatEffect : Effect<FloatEffectEvent> {
    /**
     * The current animated float value.
     */
    val value: Float

    /**
     * The default float value when the effect is idle.
     * */
    val defaultValue: Float

    /**
     * Returns the underlying [Animatable] managing the float animations.
     * */
    fun asAnimatable(): Animatable<Float, AnimationVector1D>
}

/**
 * A simple implementation of [FloatEffectEvent] with a single target value.
 *
 * @property value The target float value to animate to.
 * @property animationSpec The animation specification that controls the animation behavior.
 * @property delay The delay in milliseconds before triggering the onComplete callback.
 * @property onStart Callback invoked when the animation starts.
 * @property onComplete Callback invoked when the animation completes.
 */
@Immutable
class SingleFloatEffectEvent(
    override val value: Float,
    override val animationSpec: AnimationSpec<Float>,
    override val delay: Long = 0L,
    override val onStart: OnEffect = null,
    override val onComplete: OnEffect = null,
) : FloatEffectEvent {

    override fun copy(
        value: Float,
        animationSpec: AnimationSpec<Float>,
        delay: Long,
        onStart: OnEffect,
        onComplete: OnEffect,
    ): SingleFloatEffectEvent {
        return SingleFloatEffectEvent(
            value = value,
            animationSpec = animationSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete
        )
    }

    override fun copy(
        delay: Long,
        onStart: OnEffect,
        onComplete: OnEffect,
    ): SingleFloatEffectEvent {
        return copy(
            value = this.value,
            animationSpec = this.animationSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete
        )
    }

    override fun equals(other: Any?): Boolean {
        return FloatEffectDefaults.equals(this, other)
    }

    override fun hashCode(): Int {
        return FloatEffectDefaults.hashCode(this)
    }
}

/**
 * An implementation of [FloatEffectEvent] with separate enter and exit animations.
 *
 * This event animates to an enter value, then animates to an exit value, making it suitable
 * for effects like flash animations or temporary highlights.
 *
 * @property value The target float value for the enter animation.
 * @property exitValue The target float value for the exit animation.
 * @property animationSpec The animation specification for the enter animation.
 * @property exitAnimationSpec The animation specification for the exit animation.
 * @property delay The delay in milliseconds before triggering the onComplete callback.
 * @property onStart Callback invoked when the animation starts.
 * @property onComplete Callback invoked when the animation completes.
 */
@Immutable
class DualFloatEffectEvent(
    override val value: Float,
    val exitValue: Float,
    override val animationSpec: AnimationSpec<Float>,
    val exitAnimationSpec: AnimationSpec<Float>,
    override val delay: Long = 0L,
    override val onStart: OnEffect = null,
    override val onComplete: OnEffect = null,
) : FloatEffectEvent {

    fun copy(
        value: Float = this.value,
        exitValue: Float = this.exitValue,
        spec: AnimationSpec<Float> = this.animationSpec,
        exitAnimationSpec: AnimationSpec<Float> = this.exitAnimationSpec,
        delay: Long = this.delay,
        onStart: OnEffect = this.onStart,
        onComplete: OnEffect = this.onComplete,
    ): DualFloatEffectEvent {
        return DualFloatEffectEvent(
            value = value,
            exitValue = exitValue,
            animationSpec = spec,
            exitAnimationSpec = exitAnimationSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete
        )
    }

    override fun copy(
        value: Float,
        animationSpec: AnimationSpec<Float>,
        delay: Long,
        onStart: OnEffect,
        onComplete: OnEffect,
    ): DualFloatEffectEvent {
        return copy(
            value = value,
            exitValue = this.exitValue,
            spec = animationSpec,
            exitAnimationSpec = this.exitAnimationSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete
        )
    }

    override fun copy(
        delay: Long,
        onStart: OnEffect,
        onComplete: OnEffect,
    ): DualFloatEffectEvent {
        return copy(
            value = this.value,
            exitValue = this.exitValue,
            spec = this.animationSpec,
            exitAnimationSpec = this.exitAnimationSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DualFloatEffectEvent) return false

        if (exitValue != other.exitValue) return false
        if (exitAnimationSpec != other.exitAnimationSpec) return false

        return FloatEffectDefaults.equals(this, other)
    }

    override fun hashCode(): Int {
        var result = exitValue.hashCode()
        result = 31 * result + exitAnimationSpec.hashCode()
        result = 31 * result + FloatEffectDefaults.hashCode(this)
        return result
    }

}

/**
 * Default implementation of [FloatEffect] that manages float-based animations.
 *
 * This effect executes animations sequentially, running each event to completion before processing the next.
 * For handling multiple concurrent events, use [StackedFloatEffect] instead.
 *
 * @property scope The coroutine scope used to launch animations.
 * @property defaultValue The default float value when the effect is idle.
 */
@Stable
class DefaultFloatEffect(
    val scope: CoroutineScope,
    override val defaultValue: Float = 0f,
) : FloatEffect {
    private val _value = Animatable(defaultValue)

    override val value: Float by _value.asState()

    override var state: EffectState by mutableStateOf(Idle)

    override suspend fun reset() {
        state = Idle
        _value.snapTo(defaultValue)
    }

    override suspend fun trigger(
        event: FloatEffectEvent,
    ) {
        event.apply {
            state = Running
            onStart?.invoke()

            _value.animateTo(value, animationSpec)
            when (this) {
                is DualFloatEffectEvent -> {
                    _value.animateTo(exitValue, exitAnimationSpec)
                }

                else -> {}
            }
            state = Idle
            delay(delay)
            onComplete?.invoke()
        }
    }

    override fun tryTrigger(
        event: FloatEffectEvent,
    ) {
        scope.launch {
            trigger(
                event = event
            )
        }
    }

    override fun asAnimatable(): Animatable<Float, AnimationVector1D> = _value
}

/**
 * An implementation of [FloatEffect] that queues and processes multiple events sequentially.
 *
 * This effect uses a channel to buffer incoming events and processes them in order.
 * When multiple events are triggered rapidly, they are queued and executed one after another,
 * ensuring smooth animation sequences without interruption.
 *
 * @property root The underlying [FloatEffect] used to execute animations.
 * @param scope The coroutine scope used to launch animations and manage the event queue.
 */
@Stable
class StackedFloatEffect(
    scope: CoroutineScope,
    defaultValue: Float = 0f,
    private val root: FloatEffect = DefaultFloatEffect(
        scope,
        defaultValue
    ),
) : FloatEffect by root {

    private val channel = Channel<FloatEffectEvent>(
        capacity = Channel.BUFFERED, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        scope.launch {
            for (event in channel) {
                triggerEffect(event)
            }
        }
    }

    private suspend fun CoroutineScope.triggerEffect(event: FloatEffectEvent) {
        root.trigger(
            event = event
        )
    }

    override suspend fun trigger(
        event: FloatEffectEvent,
    ) {
        channel.send(
            event
        )
    }

    override fun tryTrigger(
        event: FloatEffectEvent,
    ) {
        channel.trySend(
            event
        )
    }

    /**
     * Closes the event channel, preventing new events from being queued.
     *
     * @param cause Optional throwable that caused the channel to close.
     */
    fun close(cause: Throwable? = null) {
        channel.close(cause)
    }
}

/**
 * Remembers a [FloatEffect] instance that persists across recompositions.
 *
 * @param scope The coroutine scope used for animations.
 */
@Composable
fun rememberFloatEffect(
    scope: CoroutineScope = rememberCoroutineScope(),
): FloatEffect {
    return remember(scope) {
        DefaultFloatEffect(
            scope = scope,
        )
    }
}

/**
 * Permits property delegation of `val`s using `by` for [FloatEffect].
 * */
@Suppress("NOTHING_TO_INLINE")
inline operator fun FloatEffect.getValue(thisObj: Any?, property: KProperty<*>): Float = value

/**
 * Triggers a flash animation effect using this [FloatEffect].
 *
 * This is a convenience suspend function that creates and triggers a flash event with customizable parameters.
 *
 * @param enterValue The target value for the enter animation.
 * @param enterSpec The animation specification for the enter animation.
 * @param exitValue The target value for the exit animation.
 * @param exitSpec The animation specification for the exit animation.
 * @param delay The delay in milliseconds before triggering the onComplete callback.
 * @param onStart Callback invoked when the animation starts.
 * @param onComplete Callback invoked when the animation completes.
 *
 * @see FloatEffectDefaults.flashEvent
 * @see FloatEffect.trigger
 */
suspend fun FloatEffect.triggerFlash(
    enterValue: Float? = null,
    enterSpec: AnimationSpec<Float>? = null,
    exitValue: Float? = null,
    exitSpec: AnimationSpec<Float>? = null,
    delay: Long? = null,
    onStart: OnEffect = null,
    onComplete: OnEffect = null,
) {
    trigger(
        FloatEffectDefaults.flashEvent(
            enterValue = enterValue,
            enterSpec = enterSpec,
            exitValue = exitValue,
            exitSpec = exitSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    )
}

/**
 * Attempts to trigger a flash animation effect using this [FloatEffect] without suspending.
 *
 * This is a convenience function that creates and triggers a flash event with customizable parameters.
 * Unlike [triggerFlash], this function does not suspend and will launch the animation in the effect's scope.
 *
 * @param enterValue The target value for the enter animation.
 * @param enterSpec The animation specification for the enter animation.
 * @param exitValue The target value for the exit animation.
 * @param exitSpec The animation specification for the exit animation.
 * @param delay The delay in milliseconds before triggering the onComplete callback.
 * @param onStart Callback invoked when the animation starts.
 * @param onComplete Callback invoked when the animation completes.
 *
 * @see FloatEffectDefaults.flashEvent
 * @see FloatEffect.tryTrigger
 */
fun FloatEffect.tryTriggerFlash(
    enterValue: Float? = null,
    enterSpec: AnimationSpec<Float>? = null,
    exitValue: Float? = null,
    exitSpec: AnimationSpec<Float>? = null,
    delay: Long? = null,
    onStart: OnEffect = null,
    onComplete: OnEffect = null,
) {
    tryTrigger(
        FloatEffectDefaults.flashEvent(
            enterValue = enterValue,
            enterSpec = enterSpec,
            exitValue = exitValue,
            exitSpec = exitSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    )
}
