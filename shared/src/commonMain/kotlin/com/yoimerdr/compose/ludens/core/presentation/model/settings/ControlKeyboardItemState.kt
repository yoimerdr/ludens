package com.yoimerdr.compose.ludens.core.presentation.model.settings

import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.KeyboardKey

/**
 * Represents a key-bound control item state in the presentation layer.
 *
 * This is a specialized shared implementation of [ControlItemState] for controls that are
 * associated with keyboard keys.
 *
 * @property type The [ItemType] that identifies this control item.
 * @property enabled Whether this control item is currently enabled.
 * @property alpha The opacity level of this control item (0.0 to 1.0).
 * @property key The [KeyboardKey] associated with this control for keyboard key.
 */
abstract class ControlKeyboardItemState<Key : KeyboardKey>(
    type: ItemType,
    enabled: Boolean,
    alpha: Float,
    val key: Key,
) : ControlItemState(type, enabled, alpha) {

    /**
     * Creates a copy of this control key item with optionally modified properties.
     *
     * This overload maintains the current key binding.
     *
     * @param type The [ItemType] for the copy. Defaults to the current type.
     * @param enabled The enabled state for the copy. Defaults to the current enabled state.
     * @param alpha The opacity level for the copy. Defaults to the current alpha value.
     * @return A new [ControlKeyboardItemState] instance with the specified properties.
     */
    override fun copy(
        type: ItemType,
        enabled: Boolean,
        alpha: Float,
    ): ControlKeyboardItemState<Key> = copy(key, type, enabled, alpha)

    override fun equals(other: Any?): Boolean {
        if (other !is ControlKeyboardItemState<Key>) return super.equals(other)

        return key == other.key && enabled == other.enabled &&
                alpha == other.alpha && type == other.type
    }

    override fun hashCode(): Int {
        val result = super.hashCode()

        return 31 * result + key.hashCode()
    }

    /**
     * Creates a copy of this control key item with all properties optionally modified.
     *
     * This overload allows changing the key binding along with other properties.
     *
     * @param key The [KeyboardKey] for the copy. Defaults to the current key.
     * @param type The [ItemType] for the copy. Defaults to the current type.
     * @param enabled The enabled state for the copy. Defaults to the current enabled state.
     * @param alpha The opacity level for the copy. Defaults to the current alpha value.
     * @return A new [ControlKeyboardItemState] instance with the specified properties.
     */
    abstract fun copy(
        key: Key,
        type: ItemType = this.type,
        enabled: Boolean = this.enabled,
        alpha: Float = this.alpha,
    ): ControlKeyboardItemState<Key>
}