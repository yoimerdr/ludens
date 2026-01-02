package com.yoimerdr.compose.ludens.core.domain.value

import kotlin.jvm.JvmInline

/**
 * Represents a volume level constrained between 0 and 100.
 *
 * This value class provides a type-safe way to work with volume values, ensuring they remain
 * within the valid range. It includes predefined constants for common volume levels and
 * operations for adjusting volume.
 *
 * @property value The volume level, must be between 0 (muted) and 100 (maximum).
 * @throws IllegalArgumentException if the value is outside the valid range [0, 100].
 */
@JvmInline
value class Volume private constructor(val value: Int) {

    init {
        require(value in RANGE) {
            "Volume must be between 0 and 100, got: $value"
        }
    }

    companion object {
        /** The valid range for volume values: 0 to 100 */
        val RANGE = 0..100

        /**
         * Creates a Volume instance with the specified value.
         * @throws IllegalArgumentException if value is outside [0, 100].
         */
        fun of(value: Int): Volume = Volume(value)

        /**
         * Creates a Volume instance if the value is valid, otherwise returns null.
         * @return Volume instance or null if value is outside [0, 100].
         */
        fun ofOrNull(value: Int): Volume? =
            if (value in RANGE) Volume(value) else null

        /**
         * Creates a Volume instance, coercing the value into the valid range.
         * @return Volume instance with value clamped to [0, 100].
         */
        fun coerce(value: Int): Volume = Volume(value.coerceIn(RANGE))

        /** Muted volume (0) */
        val Muted = Volume(0)
        /** Low volume (25) */
        val Low = Volume(25)
        /** Medium volume (50) */
        val Medium = Volume(50)
        /** High volume (75) */
        val High = Volume(75)
        /** Maximum volume (100) */
        val Max = Volume(100)
    }

    /** Returns true if the volume is muted (0) */
    val isMuted: Boolean get() = value == 0

    /** Returns the volume as a percentage (0.0 to 1.0) */
    val percentage: Float get() = value / 100f

    /**
     * Increases the volume by the specified amount.
     * @param amount The amount to increase (default: 10).
     * @return A new Volume with increased value, clamped to [0, 100].
     */
    fun increase(amount: Int = 10): Volume = coerce(value + amount)

    /**
     * Decreases the volume by the specified amount.
     * @param amount The amount to decrease (default: 10).
     * @return A new Volume with decreased value, clamped to [0, 100].
     */
    fun decrease(amount: Int = 10): Volume = coerce(value - amount)

    /**
     * Adds two volume values, coercing the result to valid range.
     * @return A new Volume with the sum of both values, clamped to [0, 100].
     */
    operator fun plus(other: Volume): Volume = coerce(value + other.value)

    /**
     * Subtracts a volume value from this one, coercing the result to valid range.
     * @return A new Volume with the difference, clamped to [0, 100].
     */
    operator fun minus(other: Volume): Volume = coerce(value - other.value)
}