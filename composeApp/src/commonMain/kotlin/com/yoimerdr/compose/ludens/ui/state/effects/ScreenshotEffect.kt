package com.yoimerdr.compose.ludens.ui.state.effects

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 * Represents the state of a screenshot effect animation.
 *
 * This sealed interface extends [EffectState] to provide specific states for the screenshot animation sequence.
 */
sealed interface ScreenshotEffectState : EffectState

/**
 * Represents the flashing state of the screenshot effect.
 *
 * This state indicates that the flash effect is currently being executed.
 */
object Flashing : ScreenshotEffectState

/**
 * Represents the transforming state of the screenshot effect.
 *
 * This state indicates that scale and offset transformations are being animated.
 */
object Transforming : ScreenshotEffectState

/**
 * Represents the hiding state of the screenshot effect.
 *
 * This state indicates that the content is being hidden/faded out.
 */
object Hiding : ScreenshotEffectState

/**
 * Default values and utility functions for [ScreenshotEffect] and [ScreenshotEffectEvent].
 *
 * Provides pre-configured animation specifications and helper methods for creating and comparing screenshot effect events.
 */
object ScreenshotEffectDefaults {
    private var _cachedSimpleScreenshotEvent: ScreenshotEffectEvent? = null

    /**
     * The default screenshot effect event with predefined animation values.
     *
     * This event includes flash, hide, scale, and offset animations configured for a typical screenshot effect.
     */
    internal val defaultSimpleScreenshotEvent: ScreenshotEffectEvent
        get() = _cachedSimpleScreenshotEvent ?: SimpleScreenshotEffectEvent(
            flash = FloatEffectDefaults.flashEvent(),
            hide = FloatEffectDefaults.event(
                value = 0f, animationSpec = tween(
                    durationMillis = 300,
                )
            ),
            scale = FloatEffectDefaults.event(
                value = 0.1f,
                animationSpec = spring(
                    stiffness = Spring.StiffnessLow,
                    dampingRatio = Spring.DampingRatioLowBouncy
                )
            ),
            offset = OffsetEffectDefaults.event(),
            delay = 0L,
        ).also {
            _cachedSimpleScreenshotEvent = it
        }

    /**
     * Creates a default screenshot effect event with predefined animation values.
     *
     * This should be used with [ScreenshotEffectEvent.copy] to specify the target values.
     */
    fun event(): ScreenshotEffectEvent = defaultSimpleScreenshotEvent

    /**
     * Creates a customizable screenshot effect event.
     *
     * @param flash The flash effect event for the camera flash animation.
     * @param hide The hide effect event for fading out the content.
     * @param scale The scale effect event for size transformation.
     * @param offset The offset effect event for position transformation.
     * @param delay The delay in milliseconds before triggering onComplete callback.
     * @param onStart Callback invoked when the animation starts.
     * @param onComplete Callback invoked when the animation completes.
     */
    fun event(
        flash: FloatEffectEvent? = null,
        hide: FloatEffectEvent? = null,
        scale: FloatEffectEvent? = null,
        offset: OffsetEffectEvent? = null,
        delay: Long? = null,
        onStart: OnEffect = null,
        onComplete: OnEffect = null,
    ): ScreenshotEffectEvent {
        return defaultSimpleScreenshotEvent.let {
            it.copy(
                flash = flash ?: it.flash,
                hide = hide ?: it.hide,
                scale = scale ?: it.scale,
                offset = offset ?: it.offset,
                delay = delay ?: it.delay,
                onStart = onStart ?: it.onStart,
                onComplete = onComplete ?: it.onComplete,
            )
        }
    }

    /**
     * Compares two [ScreenshotEffectEvent] instances for equality.
     *
     * Two events are considered equal if they have the same flash, hide, scale, and offset events.
     *
     * @param event The event to compare.
     * @param other The object to compare with.
     * @see EffectEventDefaults.equals
     */
    fun equals(event: ScreenshotEffectEvent, other: Any?): Boolean {
        if (event === other) return true
        if (other !is ScreenshotEffectEvent) return false

        if (event.flash != other.flash) return false
        if (event.hide != other.hide) return false
        if (event.scale != other.scale) return false
        if (event.offset != other.offset) return false

        return EffectEventDefaults.equals(event, other)
    }

    /**
     * Generates a hash code for a [ScreenshotEffectEvent].
     *
     * @param event The event to generate a hash code for.
     * @see EffectEventDefaults.hashCode
     */
    fun hashCode(event: ScreenshotEffectEvent): Int {
        var result = event.flash.hashCode()
        result = 31 * result + event.hide.hashCode()
        result = 31 * result + event.scale.hashCode()
        result = 31 * result + event.offset.hashCode()
        result = 31 * result + EffectEventDefaults.hashCode(event)
        return result
    }
}

/**
 * Represents an event that triggers a screenshot animation effect.
 *
 * This interface defines the contract for events that orchestrate a complete screenshot animation
 * sequence including flash, hide, scale, and offset animations.
 */
interface ScreenshotEffectEvent : EffectEvent {
    /**
     * The flash effect event for the camera flash animation.
     */
    val flash: FloatEffectEvent

    /**
     * The hide effect event for fading out the content.
     */
    val hide: FloatEffectEvent

    /**
     * The scale effect event for size transformation.
     */
    val scale: FloatEffectEvent

    /**
     * The offset effect event for position transformation.
     */
    val offset: OffsetEffectEvent

    /**
     * Creates a copy of this effect event with modified properties.
     *
     * @param flash The flash effect event for the camera flash animation.
     * @param hide The hide effect event for fading out the content.
     * @param scale The scale effect event for size transformation.
     * @param offset The offset effect event for position transformation.
     * @param delay The delay in milliseconds before triggering onComplete callback.
     * @param onStart Callback invoked when the animation starts.
     * @param onComplete Callback invoked when the animation completes.
     */
    fun copy(
        flash: FloatEffectEvent = this.flash,
        hide: FloatEffectEvent = this.hide,
        scale: FloatEffectEvent = this.scale,
        offset: OffsetEffectEvent = this.offset,
        delay: Long = this.delay,
        onStart: OnEffect = this.onStart,
        onComplete: OnEffect = this.onComplete,
    ): ScreenshotEffectEvent

    override fun copy(delay: Long, onStart: OnEffect, onComplete: OnEffect): ScreenshotEffectEvent
}

/**
 * An effect that manages screenshot animation sequences.
 *
 * This effect orchestrates a complete screenshot animation including flash effects,
 * transformations (scale and offset), and visibility changes.
 */
interface ScreenshotEffect : Effect<ScreenshotEffectEvent> {

    /**
     * Resets the scale and offset transformations to their default values.
     */
    suspend fun resetTransformations()

    /**
     * Resets the flash and content visibility to their default values.
     */
    suspend fun resetVisibility()

    /**
     * Returns the underlying flash effect.
     */
    fun asFlashEffect(): FloatEffect

    /**
     * Returns the underlying offset effect for position transformations.
     */
    fun asOffsetEffect(): OffsetEffect

    /**
     * Returns the underlying scale effect for size transformations.
     */
    fun asScaleEffect(): FloatEffect

    /**
     * Returns the underlying effect for content visibility/opacity.
     */
    fun asHideContent(): FloatEffect
}

/**
 * A simple implementation of [ScreenshotEffectEvent] with comprehensive animation properties.
 *
 * @property flash The flash effect event for the camera flash animation.
 * @property hide The hide effect event for fading out the content.
 * @property scale The scale effect event for size transformation.
 * @property offset The offset effect event for position transformation.
 * @property delay The delay in milliseconds before triggering the onComplete callback.
 * @property onStart Callback invoked when the animation starts.
 * @property onComplete Callback invoked when the animation completes.
 */
@Immutable
class SimpleScreenshotEffectEvent(
    override val flash: FloatEffectEvent,
    override val hide: FloatEffectEvent,
    override val scale: FloatEffectEvent,
    override val offset: OffsetEffectEvent,
    override val delay: Long = 0L,
    override val onStart: OnEffect = null,
    override val onComplete: OnEffect = null,
) : ScreenshotEffectEvent {

    override fun copy(
        flash: FloatEffectEvent,
        hide: FloatEffectEvent,
        scale: FloatEffectEvent,
        offset: OffsetEffectEvent,
        delay: Long,
        onStart: OnEffect,
        onComplete: OnEffect,
    ): SimpleScreenshotEffectEvent {
        return SimpleScreenshotEffectEvent(
            flash = flash,
            hide = hide,
            scale = scale,
            offset = offset,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    }

    override fun copy(
        delay: Long,
        onStart: OnEffect,
        onComplete: OnEffect,
    ): SimpleScreenshotEffectEvent {
        return copy(
            flash = flash,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    }

    override fun equals(other: Any?): Boolean {
        return ScreenshotEffectDefaults.equals(this, other)
    }

    override fun hashCode(): Int {
        return ScreenshotEffectDefaults.hashCode(this)
    }
}

/**
 * Default implementation of [ScreenshotEffect] that manages screenshot animation sequences.
 *
 * This effect executes animations sequentially, running each event to completion before processing the next.
 * For handling multiple concurrent events, use [StackedEffect] with this as [StackedEffect.root] instead.
 *
 * @property scope The coroutine scope used to launch animations.
 * @param flash The flash effect for the camera flash animation.
 * @param scale The scale effect for size transformations.
 * @param offset The offset effect for position transformations.
 * @param hide The hide effect for fading out the content.
 *
 * @see StackedEffect
 */
@Stable
class DefaultScreenshotEffect(
    val scope: CoroutineScope,
    flash: FloatEffect = DefaultFloatEffect(scope, 0f),
    scale: FloatEffect = DefaultFloatEffect(scope, 1f),
    offset: OffsetEffect = DefaultOffsetEffect(scope, Offset.Zero),
    hide: FloatEffect = DefaultFloatEffect(scope, 0f),
) : ScreenshotEffect {

    private val _flash: FloatEffect = flash
    private val _scale: FloatEffect = scale
    private val _offset: OffsetEffect = offset
    private val _content: FloatEffect = hide

    override var state: EffectState by mutableStateOf(Idle)

    override suspend fun reset() {
        resetVisibility()
        resetTransformations()
        state = Idle
    }

    override suspend fun resetVisibility() {
        _flash.reset()
        _content.reset()
    }

    override suspend fun resetTransformations() {
        _scale.reset()
        _offset.reset()
    }

    override suspend fun trigger(event: ScreenshotEffectEvent) {
        scope.launch {
            // Step 1: Reset initial states to prepare for new animation
            resetTransformations()

            event.onStart?.invoke()

            val content = _content.asAnimatable()
            state = Flashing
            // Step 2: Flash Effect - Quick white flash to simulate camera capture
            _flash.trigger(event.flash)

            content.snapTo(1f)

            // Step 4: Execute animations in parallel for smooth, synchronized movement
            val transform = launch {
                // Animate all transformations simultaneously
                state = Transforming
                supervisorScope {
                    launch {
                        _scale.trigger(event.scale)
                    }

                    launch {
                        _offset.trigger(event.offset)
                    }
                }
            }

            transform.join()

            // Step 5: Final fade out - Delayed to keep thumbnail visible during flight
            state = Hiding
            _content.trigger(event.hide)
            state = Running
            delay(event.delay)
            resetVisibility()
            state = Idle
            event.onComplete?.invoke()
        }
    }

    override fun tryTrigger(event: ScreenshotEffectEvent) {
        scope.launch {
            trigger(event)
        }
    }

    override fun asScaleEffect(): FloatEffect = _scale

    override fun asFlashEffect(): FloatEffect = _flash

    override fun asOffsetEffect(): OffsetEffect = _offset

    override fun asHideContent(): FloatEffect = _content
}

typealias StackedScreenshotEffect = StackedEffect<ScreenshotEffect, ScreenshotEffectEvent>

/**
 * Triggers a screenshot animation effect using this [ScreenshotEffect].
 *
 * This is a convenience suspend function that creates and triggers a screenshot event with customizable parameters.
 *
 * @param flash The flash effect event for the camera flash animation.
 * @param hide The hide effect event for fading out the content.
 * @param scale The scale effect event for size transformation.
 * @param offset The offset effect event for position transformation.
 * @param delay The delay in milliseconds before triggering onComplete callback.
 * @param onStart Callback invoked when the animation starts.
 * @param onComplete Callback invoked when the animation completes.
 *
 * @see ScreenshotEffectDefaults.event
 * @see ScreenshotEffect.trigger
 */
suspend fun ScreenshotEffect.trigger(
    flash: FloatEffectEvent? = null,
    hide: FloatEffectEvent? = null,
    scale: FloatEffectEvent? = null,
    offset: OffsetEffectEvent? = null,
    delay: Long? = null,
    onStart: OnEffect = null,
    onComplete: OnEffect = null,
) {
    trigger(
        ScreenshotEffectDefaults.event(
            flash = flash,
            hide = hide,
            scale = scale,
            offset = offset,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    )
}

/**
 * Attempts to trigger a screenshot animation effect using this [ScreenshotEffect] without suspending.
 *
 * This is a convenience function that creates and triggers a screenshot event with customizable parameters.
 * Unlike [trigger], this function does not suspend and will launch the animation in the effect's scope.
 *
 * @param flash The flash effect event for the camera flash animation.
 * @param hide The hide effect event for fading out the content.
 * @param scale The scale effect event for size transformation.
 * @param offset The offset effect event for position transformation.
 * @param delay The delay in milliseconds before triggering onComplete callback.
 * @param onStart Callback invoked when the animation starts.
 * @param onComplete Callback invoked when the animation completes.
 *
 * @see ScreenshotEffectDefaults.event
 * @see ScreenshotEffect.tryTrigger
 */
fun ScreenshotEffect.tryTrigger(
    flash: FloatEffectEvent? = null,
    hide: FloatEffectEvent? = null,
    scale: FloatEffectEvent? = null,
    offset: OffsetEffectEvent? = null,
    delay: Long? = null,
    onStart: OnEffect = null,
    onComplete: OnEffect = null,
) {
    tryTrigger(
        ScreenshotEffectDefaults.event(
            flash = flash,
            hide = hide,
            scale = scale,
            offset = offset,
            delay = delay,
            onStart = onStart,
            onComplete = onComplete,
        )
    )
}


/**
 * Remembers a [ScreenshotEffect] instance that persists across recompositions.
 *
 * @param scope The coroutine scope used for animations.
 * @param flash The flash effect for the camera flash animation.
 * @param scale The scale effect for size transformations.
 * @param offset The offset effect for position transformations.
 * @param hide The hide effect for fading out the content.
 */
@Composable
fun rememberScreenshotEffect(
    scope: CoroutineScope = rememberCoroutineScope(),
    flash: FloatEffect = rememberFloatEffect(scope = scope, defaultValue = 0f),
    scale: FloatEffect = rememberFloatEffect(scope = scope, defaultValue = 1f),
    offset: OffsetEffect = rememberOffsetEffect(scope = scope, defaultOffset = Offset.Zero),
    hide: FloatEffect = rememberFloatEffect(scope = scope, defaultValue = 0f),
): ScreenshotEffect {
    return remember(scope, flash, scale, offset, hide) {
        DefaultScreenshotEffect(
            scope = scope,
            flash = flash,
            scale = scale,
            offset = offset,
            hide = hide,
        )
    }
}

/**
 * Remembers a [StackedScreenshotEffect] instance that persists across recompositions.
 *
 * @param scope The coroutine scope for event processing.
 * @param flash The flash effect for the camera flash animation.
 * @param scale The scale effect for size transformations.
 * @param offset The offset effect for position transformations.
 * @param hide The hide effect for for fading out the content.
 * @param root The effect implementation to wrap in a stacked effect.
 * @param capacity The capacity of the internal event channel.
 * @param onBufferOverflow The buffer overflow strategy for the internal event channel.
 *
 * @see rememberStackedEffect
 * @see rememberScreenshotEffect
 */
@Composable
fun rememberStackedScreenshotEffect(
    scope: CoroutineScope = rememberCoroutineScope(),
    flash: FloatEffect = rememberFloatEffect(scope = scope, defaultValue = 0f),
    scale: FloatEffect = rememberFloatEffect(scope = scope, defaultValue = 1f),
    offset: OffsetEffect = rememberOffsetEffect(scope = scope, defaultOffset = Offset.Zero),
    hide: FloatEffect = rememberFloatEffect(scope = scope, defaultValue = 0f),
    root: ScreenshotEffect = rememberScreenshotEffect(
        scope = scope,
        flash = flash,
        scale = scale,
        offset = offset,
        hide = hide,
    ),
    capacity: Int = Channel.UNLIMITED,
    onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND,
): StackedScreenshotEffect {
    return rememberStackedEffect(
        root = root,
        capacity = capacity,
        scope = scope,
        onBufferOverflow = onBufferOverflow
    )
}

