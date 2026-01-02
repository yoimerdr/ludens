package com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key

import com.yoimerdr.compose.ludens.core.domain.model.key.GraphicsKeyEvent
import com.yoimerdr.compose.ludens.core.domain.model.key.InputKeyEvent
import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEvent
import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventProperty
import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType
import com.yoimerdr.compose.ludens.core.domain.model.key.MovementKeyEvent
import com.yoimerdr.compose.ludens.core.domain.port.KeyEventScriptBuilder
import org.koin.core.annotation.Factory

/**
 * Converts a [KeyEvent] to a list of JavaScript event properties.
 *
 * Create a list containing basic properties for safe executing event handlers.
 *
 * @return A list of [KeyEventProperty] objects representing the event properties.
 */
private fun KeyEvent.toEventProperties(): List<KeyEventProperty> {
    return listOf(
        KeyEventProperty(
            "keyCode", code
        ), KeyEventProperty(
            "preventDefault", "function(){return true;}", true
        )
    )
}

/**
 * Converts a [KeyEvent] to a JavaScript object literal string representation.
 *
 * Transforms the event properties into a formatted JavaScript object string
 * with proper quoting for property names and values.
 *
 * @return A string representing the JavaScript object literal.
 */
private fun KeyEvent.toEventPropertiesString(): String {
    return toEventProperties()
        .joinToString(separator = ",", prefix = "{", postfix = "}") {
            val (name, value, isStrict) = it

            "'$name': ${if (!isStrict && value is CharSequence) "'${value}'" else value}"
        }
}

/**
 * Converts a [MovementKeyEvent] to a JavaScript script string.
 *
 * Generates JavaScript code that directly modifies the Input._currentState object
 * to set the movement direction state (up, down, left, right) as active or inactive.
 *
 * @return A JavaScript statement that updates the movement state.
 */
private fun MovementKeyEvent.toEventScript(): String {
    val movement = InputKey.movements.first { it.code == code }
    // edit directly for movement state
    val isActive = type == KeyEventType.Down
    val stateName = movement.name.lowercase()
    return "Input._currentState['${stateName}']=$isActive"
}

/**
 * Converts an [InputKeyEvent] to a JavaScript script string.
 *
 * Generates JavaScript code that calls the appropriate Input callback method
 * (_onKeyDown or _onKeyUp) with the event properties.
 *
 * @return A JavaScript function call for the input event.
 */
private fun InputKeyEvent.toEventScript(): String {
    // for other codes, whe use the Input callbacks
    val properties = toEventPropertiesString()
    return when (type) {
        KeyEventType.Up -> "Input._onKeyUp($properties)"
        else -> "Input._onKeyDown($properties)"
    }
}

/**
 * Converts a [GraphicsKeyEvent] to a JavaScript script string.
 *
 * Generates JavaScript code that calls the Graphics._onKeyDown method
 * with the event properties. Only key down events are used for graphics keys.
 *
 * @return A JavaScript function call for the graphics key event.
 */
private fun GraphicsKeyEvent.toEventScript(): String {
    // for graphics key, whe only use the onKeyDown event
    val properties = toEventPropertiesString()
    return "Graphics._onKeyDown($properties)"
}

/**
 * Converts a [KeyEvent] to its corresponding JavaScript event script.
 *
 * Dispatches to the appropriate conversion method based on the concrete type
 * of the key event (InputKeyEvent, GraphicsKeyEvent, or MovementKeyEvent).
 *
 * @return A JavaScript script string for the specific key event type.
 */
private fun KeyEvent.toKeyEventScript(): String {
    return when (this) {
        is InputKeyEvent -> toEventScript()
        is GraphicsKeyEvent -> toEventScript()
        is MovementKeyEvent -> toEventScript()
    }
}

/**
 * Converts a [KeyEvent] to a complete JavaScript script string.
 *
 * If the event has a timeout, wraps the event script in a setTimeout call.
 * Otherwise, returns the event script directly.
 *
 * @return A complete JavaScript statement for the key event.
 */
private fun KeyEvent.toScript(): String {
    val state = this.toKeyEventScript()
    return if (timeout != null) {
        "setTimeout(function(){$state;}, $timeout);"
    } else state
}


/**
 * Builder implementation of [KeyEventScriptBuilder].
 *
 * Builds JavaScript scripts for keyboard events that can be executed in a web view
 * to simulate user input. Supports input keys, graphics keys, and movement keys,
 * and can handle delayed execution via setTimeout.
 *
 * This class is registered as a Factory in the dependency injection container.
 */
@Factory
class JavascriptKeyEventScriptBuilder : KeyEventScriptBuilder {
    /**
     * Mutable list storing the key events to be converted to scripts.
     */
    private val scripts = mutableListOf<KeyEvent>()

    override fun add(script: KeyEvent): KeyEventScriptBuilder {
        scripts.add(script)
        return this
    }

    override fun add(transform: KeyEventScriptBuilder.() -> KeyEvent): KeyEventScriptBuilder {
        val script = transform(this)
        scripts.add(script)
        return this
    }

    override fun restart(): KeyEventScriptBuilder {
        scripts.clear()
        return this
    }

    override fun build(): String {
        return scripts.joinToString(separator = " ") {
            "${it.toScript()};"
        }.let {
            scripts.clear()
            it
        }
    }
}