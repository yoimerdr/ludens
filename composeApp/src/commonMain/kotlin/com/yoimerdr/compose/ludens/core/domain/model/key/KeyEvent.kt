package com.yoimerdr.compose.ludens.core.domain.model.key

/**
 * Sealed class representing a key event.
 *
 * This class serves as the base for all key event types in the application,
 * providing common properties and behavior.
 *
 * @property code The key code associated with this event.
 * @property type The type of event (Up or Down).
 * @property timeout Optional timeout value in milliseconds for the event.
 */
sealed class KeyEvent(
    val code: Int,
    val type: KeyEventType,
    val timeout: Int?,
) {
    /**
     * Creates a copy of this KeyEvent with optionally modified properties.
     *
     * @param code The key code (default: current code).
     * @param type The event type (default: current type).
     * @param timeout The timeout value (default: current timeout).
     * @return A new KeyEvent instance with the specified properties.
     */
    abstract fun copy(
        code: Int = this.code,
        type: KeyEventType = this.type,
        timeout: Int? = this.timeout,
    ): KeyEvent

    override fun hashCode(): Int {
        var result = code
        result = 31 * result + (timeout ?: 0)
        result = 31 * result + type.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other !is KeyEvent)
            return false

        return other.code == code &&
                other.timeout == timeout &&
                other.type == type
    }
}