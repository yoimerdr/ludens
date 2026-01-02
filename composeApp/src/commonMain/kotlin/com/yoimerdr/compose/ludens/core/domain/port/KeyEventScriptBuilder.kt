package com.yoimerdr.compose.ludens.core.domain.port

import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEvent

/**
 * Builder interface for constructing key event scripts.
 *
 * This interface provides a fluent API for building scripts from key events,
 * allowing for sequential composition of key event actions.
 */
interface KeyEventScriptBuilder {
    /**
     * Adds a key event to the script.
     *
     * @param script The key event to add.
     * @return This builder for method chaining.
     */
    fun add(script: KeyEvent): KeyEventScriptBuilder

    /**
     * Adds a key event to the script using a transform function.
     *
     * @param transform A function that produces a KeyEvent using this builder context.
     * @return This builder for method chaining.
     */
    fun add(transform: KeyEventScriptBuilder.() -> KeyEvent): KeyEventScriptBuilder

    /**
     * Clears all previously added key events.
     *
     * @return This builder for method chaining.
     */
    fun restart(): KeyEventScriptBuilder

    /**
     * Builds the final script string from the accumulated key events.
     *
     * @return The script as a string.
     */
    fun build(): String
}