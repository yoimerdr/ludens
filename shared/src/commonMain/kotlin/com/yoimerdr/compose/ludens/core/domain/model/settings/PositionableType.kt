package com.yoimerdr.compose.ludens.core.domain.model.settings

/**
 * Enum representing types of positionable UI elements.
 *
 * @property value The integer value associated with this type.
 */
enum class PositionableType(val value: Int) {
    /** The joystick control element */
    Joystick(0),

    /** The action keys control element */
    Keys(1),

    /** The fast actions button element */
    Actions(2);

    companion object {
        /**
         * Retrieves a PositionableType from its integer value.
         *
         * @param value The integer value to convert.
         * @return The corresponding PositionableType.
         * @throws NoSuchElementException if no matching type is found.
         */
        fun from(value: Int): PositionableType = entries.first { value == it.value }

        val Settings = Actions
    }
}