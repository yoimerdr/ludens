package com.yoimerdr.compose.ludens.ui.state.effects

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.geometry.takeOrElse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

/**
 * Default values and utility functions for [OffsetEffect] and [OffsetEffectEvent].
 *
 * Provides pre-configured animation specifications and helper methods for creating and comparing offset effect events.
 */
object OffsetEffectDefaults {
    private var _cachedFullOffsetEvent: FullOffsetEffectEvent? = null
    private var _cachedAxisOffsetEvent: AxisOffsetEffectEvent? = null

    /**
     * The default full offset event with predefined animation values.
     *
     * This event is intended to be used with [FullOffsetEffectEvent.copy] to specify the target offset.
     */
    internal val defaultFullOffsetEvent: FullOffsetEffectEvent
        get() {
            return _cachedFullOffsetEvent ?: FullOffsetEffectEvent(
                animationSpec = spring(
                    stiffness = Spring.StiffnessLow
                ),
                offset = Offset.Unspecified,
                delay = 0L,
            ).also {
                _cachedFullOffsetEvent = it
            }
        }

    /**
     * The default axis offset event with predefined animation values.
     *
     * This event is intended to be used with [AxisOffsetEffectEvent.copy] to specify the target axis values.
     */
    internal val defaultAxisOffsetEvent: AxisOffsetEffectEvent
        get() {
            return defaultFullOffsetEvent.toAxisEvent()
                .also {
                    _cachedAxisOffsetEvent = it
                }
        }

    /**
     * Creates a default full offset effect event with predefined animation values.
     *
     * This should be used with [OffsetEffectEvent.copy] to specify the target offset.
     */
    fun event(): OffsetEffectEvent = defaultFullOffsetEvent

    /**
     * Creates a customizable full offset effect event.
     *
     * @param offset The target offset value to animate to.
     * @param animationSpec The animation specification for the animation.
     * @param delay The delay in milliseconds before triggering onComplete callback.
     * @param onStart Callback invoked when the animation starts.
     * @param onComplete Callback invoked when the animation completes.
     */
    fun event(
        offset: Offset,
        animationSpec: AnimationSpec<Offset>? = null,
        delay: Long? = null,
        onStart: OnEffect = null,
        onComplete: OnEffect = null,
    ): OffsetEffectEvent {
        return defaultFullOffsetEvent.let {
            it.copy(
                offset = offset,
                animationSpec = animationSpec ?: it.animationSpec,
                delay = delay ?: it.delay,
                onStart = onStart ?: it.onStart,
                onComplete = onComplete ?: it.onComplete,
            )
        }
    }

    /**
     * Creates a customizable axis offset effect event.
     *
     * @param x The target X coordinate, or null to keep the current X value.
     * @param y The target Y coordinate, or null to keep the current Y value.
     * @param animationSpec The animation specification for the animation.
     * @param delay The delay in milliseconds before triggering onComplete callback.
     * @param onStart Callback invoked when the animation starts.
     * @param onComplete Callback invoked when the animation completes.
     */
    fun event(
        x: Float?,
        y: Float? = null,
        animationSpec: AnimationSpec<Offset>? = null,
        delay: Long? = null,
        onStart: OnEffect = null,
        onComplete: OnEffect = null,
    ): OffsetEffectEvent {
        return defaultAxisOffsetEvent.let {
            it.copy(
                x = x,
                y = y,
                animationSpec = animationSpec ?: it.animationSpec,
                delay = delay ?: it.delay,
                onStart = onStart ?: it.onStart,
                onComplete = onComplete ?: it.onComplete,
            )
        }
    }

    /**
     * Compares two [OffsetEffectEvent] instances for equality.
     *
     * Two events are considered equal if they have the same animation specification and base properties.
     *
     * @param event The event to compare.
     * @param other The object to compare with.
     * @see EffectEventDefaults.equals
     */
    fun equals(event: OffsetEffectEvent, other: Any?): Boolean {
        if (event === other) return true
        if (other !is OffsetEffectEvent) return false

        if (event.animationSpec != other.animationSpec) return false
        if (event.offset != other.offset) return false

        return EffectEventDefaults.equals(event, other)
    }

    /**
     * Generates a hash code for an [OffsetEffectEvent].
     *
     * @param event The event to generate a hash code for.
     * @see EffectEventDefaults.hashCode
     */
    fun hashCode(event: OffsetEffectEvent): Int {
        var result = event.animationSpec.hashCode()
        result = 31 * result + event.offset.hashCode()
        result = 31 * result + EffectEventDefaults.hashCode(event)
        return result
    }
}

/**
 * Represents an event that triggers an offset-based animation effect.
 *
 * This interface defines the contract for events that animate an offset value with a specific animation specification.
 */
interface OffsetEffectEvent : EffectEvent {
    /**
     * The target offset value to animate to.
     */
    val offset: Offset

    /**
     * The animation specification that controls how the offset animates.
     */
    val animationSpec: AnimationSpec<Offset>

    /**
     * Creates a copy of this effect event with modified properties.
     *
     * @param offset The target offset value.
     * @param animationSpec The animation specification.
     * @param delay The delay before triggering callbacks.
     * @param onStart Callback invoked when animation starts.
     * @param onComplete Callback invoked when animation completes.
     */
    fun copy(
        offset: Offset = this.offset,
        animationSpec: AnimationSpec<Offset> = this.animationSpec,
        delay: Long = this.delay,
        onStart: OnEffect = this.onStart,
        onComplete: OnEffect = this.onComplete,
    ): OffsetEffectEvent

    override fun copy(delay: Long, onStart: OnEffect, onComplete: OnEffect): OffsetEffectEvent
}


/**
 * An effect that manages offset-based animations.
 */
interface OffsetEffect : Effect<OffsetEffectEvent> {

    /**
     * The current animated offset value.
     */
    val offset: Offset

    /**
     * The default float value when the effect is idle.
     * */
    val defaultOffset: Offset

    /**
     * Returns the underlying [Animatable] managing the float animations.
     * */
    fun asAnimatable(): Animatable<Offset, AnimationVector2D>
}


/**
 * A simple implementation of [OffsetEffectEvent] with a single target offset value.
 *
 * @property offset The target offset value to animate to.
 * @property animationSpec The animation specification that controls the animation behavior.
 * @property delay The delay in milliseconds before triggering the onComplete callback.
 * @property onStart Callback invoked when the animation starts.
 * @property onComplete Callback invoked when the animation completes.
 */
@Immutable
class FullOffsetEffectEvent(
    offset: Offset = Offset.Zero,
    override val animationSpec: AnimationSpec<Offset>,
    override val delay: Long = 0L,
    override val onStart: OnEffect = null,
    override val onComplete: OnEffect = null,
) : OffsetEffectEvent {

    override val offset: Offset = offset.takeOrElse { Offset.Zero }

    override fun copy(
        offset: Offset,
        animationSpec: AnimationSpec<Offset>,
        delay: Long,
        onStart: OnEffect,
        onComplete: OnEffect,
    ): FullOffsetEffectEvent {
        return FullOffsetEffectEvent(
            offset = offset,
            animationSpec = animationSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    }

    override fun copy(
        delay: Long,
        onStart: OnEffect,
        onComplete: OnEffect,
    ): FullOffsetEffectEvent {
        return copy(
            offset = this.offset,
            animationSpec = this.animationSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    }

    override fun equals(other: Any?): Boolean {
        return OffsetEffectDefaults.equals(this, other)
    }

    override fun hashCode(): Int {
        return OffsetEffectDefaults.hashCode(this)
    }
}


/**
 * An implementation of [OffsetEffectEvent] that allows independent animation of X and Y axes.
 *
 * This event animates only the specified axis values, leaving unspecified axes at their current values.
 * This is useful for animating horizontal or vertical movement independently.
 *
 * @property x The target X coordinate, or null to keep the current X value.
 * @property y The target Y coordinate, or null to keep the current Y value.
 * @property animationSpec The animation specification for the animation.
 * @property delay The delay in milliseconds before triggering the onComplete callback.
 * @property onStart Callback invoked when the animation starts.
 * @property onComplete Callback invoked when the animation completes.
 */
@Immutable
class AxisOffsetEffectEvent(
    val x: Float?,
    val y: Float?,
    override val animationSpec: AnimationSpec<Offset>,
    override val delay: Long = 0L,
    override val onStart: OnEffect = null,
    override val onComplete: OnEffect = null,
) : OffsetEffectEvent {
    /**
     * Unused and unspecified offset property to satisfy [OffsetEffectEvent] interface.
     * */
    override val offset: Offset = Offset.Unspecified

    fun copy(
        x: Float? = this.x,
        y: Float? = this.y,
        animationSpec: AnimationSpec<Offset> = this.animationSpec,
        delay: Long = this.delay,
        onStart: OnEffect = this.onStart,
        onComplete: OnEffect = this.onComplete,
    ): AxisOffsetEffectEvent {
        return AxisOffsetEffectEvent(
            x = x,
            y = y,
            animationSpec = animationSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    }

    override fun copy(
        offset: Offset,
        animationSpec: AnimationSpec<Offset>,
        delay: Long,
        onStart: OnEffect,
        onComplete: OnEffect,
    ): AxisOffsetEffectEvent {
        val isValid = offset.isSpecified
        return copy(
            x = if (isValid) offset.x else x,
            y = if (isValid) offset.y else y,
            animationSpec = animationSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    }

    override fun copy(
        delay: Long,
        onStart: OnEffect,
        onComplete: OnEffect,
    ): AxisOffsetEffectEvent {
        return copy(
            offset = this.offset,
            animationSpec = this.animationSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AxisOffsetEffectEvent) return false

        if (x != other.x) return false
        if (y != other.y) return false

        return OffsetEffectDefaults.equals(this, other)
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + OffsetEffectDefaults.hashCode(this)
        return result
    }
}

/**
 * Default implementation of [OffsetEffect] that manages offset-based animations.
 *
 * This effect executes animations sequentially, running each event to completion before processing the next.
 * For handling multiple concurrent events, use [StackedEffect] with this as [StackedEffect.root] instead.
 *
 * @property scope The coroutine scope used to launch animations.
 * @property defaultOffset The default offset value when the effect is idle.
 */
@Stable
class DefaultOffsetEffect(
    val scope: CoroutineScope,
    defaultOffset: Offset = Offset.Zero,
) : OffsetEffect {

    private val _offset =
        Animatable(defaultOffset.takeOrElse { Offset.Zero }, Offset.VectorConverter)

    override val offset: Offset by _offset.asState()

    override var state: EffectState by mutableStateOf(Idle)

    override val defaultOffset: Offset = defaultOffset.takeOrElse { Offset.Zero }

    override suspend fun reset() {
        state = Idle
        _offset.snapTo(defaultOffset)
    }

    override suspend fun trigger(
        event: OffsetEffectEvent,
    ) {
        val targetX: Float
        val targetY: Float

        when (event) {
            is AxisOffsetEffectEvent -> {
                val currentOffset = offset

                targetX = event.x ?: currentOffset.x
                targetY = event.y ?: currentOffset.y
            }

            else -> {
                targetX = event.offset.x
                targetY = event.offset.y
            }
        }

        event.apply {
            state = Running
            onStart?.invoke()
            _offset.animateTo(
                Offset(
                    x = targetX,
                    y = targetY,
                ),
                animationSpec = animationSpec,
            )
            state = Idle
            delay(delay)
            onComplete?.invoke()
        }
    }

    override fun tryTrigger(
        event: OffsetEffectEvent,
    ) {
        scope.launch {
            trigger(
                event = event
            )
        }
    }

    override fun asAnimatable(): Animatable<Offset, AnimationVector2D> = _offset
}

typealias StackedOffsetEffect = StackedEffect<OffsetEffect, OffsetEffectEvent>

/**
 * Permits property delegation of `val`s using `by` for [OffsetEffect].
 * */
@Suppress("NOTHING_TO_INLINE")
inline operator fun OffsetEffect.getValue(thisObj: Any?, property: KProperty<*>): Offset = offset

/**
 * Triggers an offset animation effect using this [OffsetEffect].
 *
 * This is a convenience suspend function that creates and triggers an offset event with a target offset value.
 *
 * @param offset The target offset value to animate to.
 * @param animationSpec The animation specification for the animation.
 * @param delay The delay in milliseconds before triggering the onComplete callback.
 * @param onStart Callback invoked when the animation starts.
 * @param onComplete Callback invoked when the animation completes.
 *
 * @see OffsetEffect.trigger
 * @see OffsetEffectDefaults.event
 */
suspend fun OffsetEffect.trigger(
    offset: Offset,
    animationSpec: AnimationSpec<Offset>? = null,
    delay: Long? = null,
    onStart: OnEffect = null,
    onComplete: OnEffect = null,
) {
    trigger(
        OffsetEffectDefaults.event(
            offset = offset,
            animationSpec = animationSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    )
}

/**
 * Attempts to trigger an offset animation effect using this [OffsetEffect] without suspending.
 *
 * This is a convenience function that creates and triggers an offset event with a target offset value.
 * Unlike [trigger], this function does not suspend and will launch the animation in the effect's scope.
 *
 * @param offset The target offset value to animate to.
 * @param animationSpec The animation specification for the animation.
 * @param delay The delay in milliseconds before triggering the onComplete callback.
 * @param onStart Callback invoked when the animation starts.
 * @param onComplete Callback invoked when the animation completes.
 *
 * @see OffsetEffect.tryTrigger
 * @see OffsetEffectDefaults.event
 */
fun OffsetEffect.tryTrigger(
    offset: Offset,
    animationSpec: AnimationSpec<Offset>? = null,
    delay: Long? = null,
    onStart: OnEffect = null,
    onComplete: OnEffect = null,
) {
    tryTrigger(
        OffsetEffectDefaults.event(
            offset = offset,
            animationSpec = animationSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    )
}

/**
 * Triggers an offset animation effect with independent axis control using this [OffsetEffect].
 *
 * This is a convenience suspend function that creates and triggers an axis offset event,
 * allowing independent animation of X and Y coordinates. Null values will keep the current axis position.
 *
 * @param x The target X coordinate, or null to keep the current X value.
 * @param y The target Y coordinate, or null to keep the current Y value.
 * @param animationSpec The animation specification for the animation.
 * @param delay The delay in milliseconds before triggering the onComplete callback.
 * @param onStart Callback invoked when the animation starts.
 * @param onComplete Callback invoked when the animation completes.
 *
 * @see OffsetEffect.trigger
 * @see OffsetEffectDefaults.event
 */
suspend fun OffsetEffect.trigger(
    x: Float?,
    y: Float? = null,
    animationSpec: AnimationSpec<Offset>? = null,
    delay: Long? = null,
    onStart: OnEffect = null,
    onComplete: OnEffect = null,
) {
    trigger(
        OffsetEffectDefaults.event(
            x = x,
            y = y,
            animationSpec = animationSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    )
}

/**
 * Attempts to trigger an offset animation effect with independent axis control using this [OffsetEffect] without suspending.
 *
 * This is a convenience function that creates and triggers an axis offset event,
 * allowing independent animation of X and Y coordinates. Null values will keep the current axis position.
 * Unlike [trigger], this function does not suspend and will launch the animation in the effect's scope.
 *
 * @param x The target X coordinate, or null to keep the current X value.
 * @param y The target Y coordinate, or null to keep the current Y value.
 * @param animationSpec The animation specification for the animation.
 * @param delay The delay in milliseconds before triggering the onComplete callback.
 * @param onStart Callback invoked when the animation starts.
 * @param onComplete Callback invoked when the animation completes.
 *
 * @see OffsetEffect.tryTrigger
 * @see OffsetEffectDefaults.event
 */
fun OffsetEffect.tryTrigger(
    x: Float?,
    y: Float? = null,
    animationSpec: AnimationSpec<Offset>? = null,
    delay: Long? = null,
    onStart: OnEffect = null,
    onComplete: OnEffect = null,
) {
    tryTrigger(
        OffsetEffectDefaults.event(
            x = x,
            y = y,
            animationSpec = animationSpec,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    )
}

/**
 * Converts this [FullOffsetEffectEvent] to an [AxisOffsetEffectEvent].
 *
 * @param x The target X coordinate.
 * @param y The target Y coordinate.
 * @param animationSpec The animation specification.
 * @param delay The delay in milliseconds.
 * @param onStart Callback invoked when animation starts.
 * @param onComplete Callback invoked when animation completes.
 */
fun FullOffsetEffectEvent.toAxisEvent(
    x: Float? = this.offset.x,
    y: Float? = this.offset.y,
    animationSpec: AnimationSpec<Offset> = this.animationSpec,
    delay: Long = this.delay,
    onStart: OnEffect = this.onStart,
    onComplete: OnEffect = this.onComplete,
): AxisOffsetEffectEvent {
    return AxisOffsetEffectEvent(
        x = x,
        y = y,
        animationSpec = animationSpec,
        delay = delay,
        onStart = onStart,
        onComplete = onComplete,
    )
}

/**
 * Converts this [AxisOffsetEffectEvent] to a [FullOffsetEffectEvent].
 *
 * @param offset The target offset value.
 * @param animationSpec The animation specification.
 * @param delay The delay in milliseconds.
 * @param onStart Callback invoked when animation starts.
 * @param onComplete Callback invoked when animation completes.
 */
fun AxisOffsetEffectEvent.toFullEvent(
    offset: Offset = Offset(
        x = this.x ?: 0f,
        y = this.y ?: 0f,
    ),
    animationSpec: AnimationSpec<Offset> = this.animationSpec,
    delay: Long = this.delay,
    onStart: OnEffect = this.onStart,
    onComplete: OnEffect = this.onComplete,
): FullOffsetEffectEvent {
    return FullOffsetEffectEvent(
        offset = offset,
        animationSpec = animationSpec,
        delay = delay,
        onStart = onStart,
        onComplete = onComplete,
    )
}


/**
 * Remembers an [OffsetEffect] instance that persists across recompositions.
 *
 * @param scope The coroutine scope used for animations.
 * @param defaultOffset The default offset value when the effect is idle.
 */
@Composable
fun rememberOffsetEffect(
    scope: CoroutineScope = rememberCoroutineScope(),
    defaultOffset: Offset = Offset.Zero,
): OffsetEffect {
    return remember(scope, defaultOffset) {
        DefaultOffsetEffect(
            scope = scope,
            defaultOffset = defaultOffset,
        )
    }
}

/**
 * Remembers a [StackedOffsetEffect] instance that persists across recompositions.
 *
 * @param scope The coroutine scope for event processing.
 * @param root The effect implementation to wrap in a stacked effect.
 * @param defaultOffset The default offset value when the effect is idle.
 * @param capacity The capacity of the internal event channel.
 * @param onBufferOverflow The buffer overflow strategy for the internal event channel.
 *
 * @see rememberStackedEffect
 * @see rememberOffsetEffect
 */
@Composable
fun rememberStackedOffsetEffect(
    scope: CoroutineScope = rememberCoroutineScope(),
    defaultOffset: Offset = Offset.Zero,
    root: OffsetEffect = rememberOffsetEffect(
        scope = scope,
        defaultOffset = defaultOffset,
    ),
    capacity: Int = Channel.UNLIMITED,
    onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND,
): StackedOffsetEffect {
    return rememberStackedEffect(
        root = root,
        capacity = capacity,
        scope = scope,
        onBufferOverflow = onBufferOverflow,
    )
}