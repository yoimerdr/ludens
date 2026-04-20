package com.yoimerdr.compose.ludens.ui.state.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

/**
 * Represents the state of an effect or operation.
 */
sealed interface EffectState

/**
 * Represents the idle state of an effect.
 *
 * This state indicates that no effect is currently running or pending execution.
 */
object Idle : EffectState

/**
 * Represents the running state of an effect.
 *
 * This state indicates that an effect is currently being executed.
 */
object Running : EffectState

/**
 * Type alias for a callback function invoked when an effect starts or completes.
 *
 * This nullable function type allows consumers to provide optional starting or completion
 * handlers that will be executed before or after an effect.
 */
typealias OnEffect = (suspend () -> Unit)?

/**
 * Represents an event that configures an effect's behavior.
 *
 * This interface defines the basic structure for effect events, including
 * timing and lifecycle callbacks.
 */
interface EffectEvent {
    /**
     * The delay in milliseconds before the effect is marked as complete.
     */
    val delay: Long

    /**
     * Optional callback invoked when the effect starts.
     */
    val onStart: OnEffect

    /**
     * Optional callback invoked when the effect completes.
     */
    val onComplete: OnEffect

    /**
     * Creates a copy of this effect event with optionally modified properties.
     *
     * @param delay The delay for the new event.
     * @param onStart The start callback for the new event.
     * @param onComplete The completion callback for the new event.
     */
    fun copy(
        delay: Long = this.delay,
        onStart: OnEffect = this.onStart,
        onComplete: OnEffect = this.onComplete,
    ): EffectEvent

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int
}

/**
 * Provides default values and utility methods for [EffectEvent] implementations.
 */
object EffectEventDefaults {
    private var _cachedDelay: Long? = null

    internal val defaultDelay: Long
        get() = _cachedDelay ?: 700L.also {
            _cachedDelay = it
        }

    /**
     * The default delay in milliseconds before the effect is marked as complete.
     */
    val delay: Long
        get() = defaultDelay

    /**
     * Compares two [EffectEvent] instances for equality.
     *
     * Two events are considered equal if they have the same values.
     *
     * @param event The event to compare.
     * @param other The object to compare with.
     */
    fun equals(event: EffectEvent, other: Any?): Boolean {
        if (event === other) return true
        if (other !is EffectEvent) return false

        if (event.delay != other.delay) return false
        if (event.onStart != other.onStart) return false
        if (event.onComplete != other.onComplete) return false

        return true
    }

    /**
     * Generates a hash code for a [EffectEvent].
     *
     * @param event The event to generate a hash code for.
     */
    fun hashCode(event: EffectEvent): Int {
        var result = event.delay.hashCode()
        result = 31 * result + event.onStart.hashCode()
        result = 31 * result + event.onComplete.hashCode()
        return result
    }
}

/**
 * An effect that can be triggered and managed.
 *
 * @param E The type of [EffectEvent] this effect accepts.
 */
interface Effect<E : EffectEvent> {
    /**
     * The current state of the effect.
     */
    val state: EffectState

    /**
     * Resets the effect to its initial state.
     */
    suspend fun reset()

    /**
     * Triggers the effect with the given event, suspending until completion.
     *
     * @param event The event configuration for this effect execution.
     */
    suspend fun trigger(event: E)

    /**
     * Attempts to trigger the effect without suspending.
     *
     * @param event The event configuration for this effect execution.
     */
    fun tryTrigger(event: E)
}


/**
 * Checks if the effect is not currently [Idle].
 *
 * @return `true` if the effect is not [Idle] state, `false` otherwise.
 */
val Effect<*>.isRunning: Boolean
    get() = state !is Idle

/**
 * A wrapper around an [Effect] that queues and executes effect events sequentially.
 *
 * This class manages a queue of effect events and ensures they are executed in order,
 * preventing concurrent execution. Events are processed through a channel with unlimited
 * capacity by default, guaranteeing that no events are lost.
 *
 * @param T The type of the underlying [Effect] implementation.
 * @param E The type of [EffectEvent] this effect accepts.
 * @param scope The [CoroutineScope] used for launching the event processing coroutine.
 * @param root The underlying effect implementation that will process the events.
 *
 * @see Effect
 * @see EffectEvent
 */
@Stable
class StackedEffect<T : Effect<E>, E : EffectEvent>(
    scope: CoroutineScope,
    val root: T,
    capacity: Int = Channel.UNLIMITED,
    onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND,
) : Effect<E> by root {
    private val channel = Channel<Set<E>>(
        capacity = capacity,
        onBufferOverflow = onBufferOverflow
    )

    init {
        scope.launch {
            for (events in channel) {
                events.forEach {
                    root.trigger(
                        event = it
                    )
                }
            }
        }
    }

    /**
     * Triggers the effect with the given events, suspending until completion.
     *
     * @param event The event configuration for this effect execution.
     * @param events Other sequential event configurations to execute.
     */
    suspend fun trigger(
        event: E,
        vararg events: E,
    ) {
        channel.send(
            setOf(event, *events)
        )
    }

    /**
     * Attempts to trigger the effect without suspending.
     *
     * @param event The event configuration for this effect execution.
     * @param events Other sequential event configurations to execute.
     */
    fun tryTrigger(
        event: E,
        vararg events: E,
    ) {
        channel.trySend(
            setOf(event, *events)
        )
    }

    override suspend fun trigger(
        event: E,
    ) {
        channel.send(
            setOf(event)
        )
    }


    override fun tryTrigger(
        event: E,
    ) {
        channel.trySend(
            setOf(event)
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
 * Permits property delegation of `val`s using `by` for [StackedEffect].
 * */
@Suppress("NOTHING_TO_INLINE")
inline operator fun <T : Effect<E>, E : EffectEvent> StackedEffect<T, E>.getValue(
    thisObj: Any?,
    property: KProperty<*>,
): T = root

/**
 * Creates and remembers a [StackedEffect] that wraps the provided effect.
 *
 * The stacked effect will survive recompositions and uses the provided or remembered
 * [CoroutineScope] for processing queued events. The effect is remembered based on
 * the provided scope and root effect, recreating it only when these change.
 *
 * @param T The type of the underlying [Effect] implementation.
 * @param E The type of [EffectEvent] this effect accepts.
 * @param root The effect implementation to wrap in a stacked effect.
 * @param scope The coroutine scope for event processing.
 * @param capacity The capacity of the internal event channel.
 * @param onBufferOverflow The buffer overflow strategy for the internal event channel.
 *
 * @see StackedEffect
 * @see rememberCoroutineScope
 */
@Composable
fun <T : Effect<E>, E : EffectEvent> rememberStackedEffect(
    root: T,
    scope: CoroutineScope = rememberCoroutineScope(),
    capacity: Int = Channel.UNLIMITED,
    onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND,
): StackedEffect<T, E> {
    return remember(scope, root, capacity, onBufferOverflow) {
        StackedEffect(
            scope = scope,
            root = root,
            capacity = capacity,
            onBufferOverflow = onBufferOverflow
        )
    }
}