package com.yoimerdr.compose.ludens.core.presentation.model.settings

import androidx.compose.runtime.Immutable
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.InputKey

/**
 * Represents a key-bound control item state in the presentation layer.
 *
 * This is a specialized implementation of [ControlItemState] for controls that are
 * associated with keyboard input. Each instance represents a mappable control that
 * triggers when a specific [InputKey] is pressed.
 *
 * @property type The [ItemType] that identifies this control item.
 * @property enabled Whether this control item is currently enabled.
 * @property alpha The opacity level of this control item (0.0 to 1.0).
 * @property key The [InputKey] associated with this control for keyboard input.
 */
@Immutable
class ControlKeyItemState(
    type: ItemType,
    enabled: Boolean,
    alpha: Float,
    val key: InputKey,
) : ControlItemState(type, enabled, alpha) {

    /**
     * Creates a copy of this control key item with optionally modified properties.
     *
     * This overload maintains the current key binding.
     *
     * @param type The [ItemType] for the copy. Defaults to the current type.
     * @param enabled The enabled state for the copy. Defaults to the current enabled state.
     * @param alpha The opacity level for the copy. Defaults to the current alpha value.
     * @return A new [ControlKeyItemState] instance with the specified properties.
     */
    override fun copy(
        type: ItemType,
        enabled: Boolean,
        alpha: Float,
    ): ControlKeyItemState = copy(key, type, enabled, alpha)

    override fun equals(other: Any?): Boolean {
        if (other !is ControlKeyItemState) return super.equals(other)

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
     * @param key The [InputKey] for the copy. Defaults to the current key.
     * @param type The [ItemType] for the copy. Defaults to the current type.
     * @param enabled The enabled state for the copy. Defaults to the current enabled state.
     * @param alpha The opacity level for the copy. Defaults to the current alpha value.
     * @return A new [ControlKeyItemState] instance with the specified properties.
     */
    fun copy(
        key: InputKey,
        type: ItemType = this.type,
        enabled: Boolean = this.enabled,
        alpha: Float = this.alpha,
    ): ControlKeyItemState = ControlKeyItemState(type, enabled, alpha, key)
}