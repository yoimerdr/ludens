package com.yoimerdr.compose.ludens.ui.state.effects

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