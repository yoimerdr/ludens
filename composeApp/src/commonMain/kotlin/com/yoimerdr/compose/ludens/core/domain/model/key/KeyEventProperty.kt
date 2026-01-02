package com.yoimerdr.compose.ludens.core.domain.model.key

/**
 * Represents a property of a key event.
 *
 * @property name The name of the property.
 * @property value The value of the property.
 * @property isStrict Whether this property must serialize strictly (default: false).
 */
data class KeyEventProperty(
    val name: String,
    val value: Any,
    val isStrict: Boolean = false,
)