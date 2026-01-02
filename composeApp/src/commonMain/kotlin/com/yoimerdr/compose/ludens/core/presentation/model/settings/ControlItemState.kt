package com.yoimerdr.compose.ludens.core.presentation.model.settings

import androidx.compose.runtime.Immutable
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType

/**
 * Base sealed class representing a control item state in the presentation layer.
 *
 * This class serves as the parent for all control item state types, providing common
 * properties and behavior for on-screen controls. Subclasses include action items
 * and key-bound items.
 *
 * @property type The [ItemType] that identifies this control item.
 * @property enabled Whether this control item is currently enabled and interactive.
 * @property alpha The opacity level of this control item (0.0 to 1.0).
 */
@Immutable
sealed class ControlItemState(
    val type: ItemType,
    val enabled: Boolean,
    val alpha: Float,
) {
    /**
     * Creates a copy of this control item with optionally modified properties.
     *
     * @param type The [ItemType] for the copy. Defaults to the current type.
     * @param enabled The enabled state for the copy. Defaults to the current enabled state.
     * @param alpha The opacity level for the copy. Defaults to the current alpha value.
     * @return A new [ControlItemState] instance of the appropriate subclass.
     */
    abstract fun copy(
        type: ItemType = this.type,
        enabled: Boolean = this.enabled,
        alpha: Float = this.alpha,
    ): ControlItemState

    override fun equals(other: Any?): Boolean {
        if (other !is ControlItemState) return false

        return enabled == other.enabled && alpha == other.alpha && type == other.type
    }

    override fun hashCode(): Int {
        var result = enabled.hashCode()
        result = 31 * result + alpha.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}
