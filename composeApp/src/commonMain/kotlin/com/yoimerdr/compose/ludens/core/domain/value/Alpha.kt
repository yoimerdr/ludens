package com.yoimerdr.compose.ludens.core.domain.value

import kotlin.jvm.JvmInline

/**
 * Represents an alpha (transparency) value constrained between 0.0 and 1.0.
 *
 * This value class provides a type-safe way to work with alpha values, ensuring they remain
 * within the valid range. It includes predefined constants for common transparency levels
 * and operations for adjusting alpha values.
 *
 * @property value The alpha value, must be between 0.0 (fully transparent) and 1.0 (fully opaque).
 * @throws IllegalArgumentException if the value is outside the valid range [0.0, 1.0].
 */
@JvmInline
value class Alpha private constructor(val value: Float) {
    init {
        require(value in RANGE) {
            "Alpha must be between 0.0 and 1.0, got: $value"
        }
    }

    companion object {
        /** The valid range for alpha values: 0.0 to 1.0 */
        val RANGE = 0f..1f

        /**
         * Creates an Alpha instance with the specified value.
         * @throws IllegalArgumentException if value is outside [0.0, 1.0].
         */
        fun of(value: Float): Alpha = Alpha(value)

        /**
         * Creates an Alpha instance if the value is valid, otherwise returns null.
         * @return Alpha instance or null if value is outside [0.0, 1.0].
         */
        fun ofOrNull(value: Float): Alpha? =
            if (value in RANGE) Alpha(value) else null

        /**
         * Creates an Alpha instance, coercing the value into the valid range.
         * @return Alpha instance with value clamped to [0.0, 1.0].
         */
        fun coerce(value: Float): Alpha = Alpha(value.coerceIn(RANGE))

        /** Fully transparent (0.0) */
        val None = Alpha(0f)
        /** Low transparency (0.15) */
        val Low = Alpha(0.15f)
        /** Medium transparency (0.4) */
        val Medium = Alpha(0.4f)
        /** High transparency (0.7) */
        val High = Alpha(0.7f)
        /** Fully opaque (1.0) */
        val Max = Alpha(1f)
    }

    /**
     * Adds two alpha values, coercing the result to valid range.
     * @return A new Alpha with the sum of both values, clamped to [0.0, 1.0].
     */
    operator fun plus(other: Alpha): Alpha = coerce(value + other.value)

    /**
     * Subtracts an alpha value from this one, coercing the result to valid range.
     * @return A new Alpha with the difference, clamped to [0.0, 1.0].
     */
    operator fun minus(other: Alpha): Alpha = coerce(value - other.value)

    /**
     * Increases the alpha value by the specified delta.
     * @param delta The amount to increase (default: 0.1).
     * @return A new Alpha with increased value, clamped to [0.0, 1.0].
     */
    fun increase(delta: Float = 0.1f): Alpha = coerce(value + delta)

    /**
     * Decreases the alpha value by the specified delta.
     * @param delta The amount to decrease (default: 0.1).
     * @return A new Alpha with decreased value, clamped to [0.0, 1.0].
     */
    fun decrease(delta: Float = 0.1f): Alpha = coerce(value - delta)
}