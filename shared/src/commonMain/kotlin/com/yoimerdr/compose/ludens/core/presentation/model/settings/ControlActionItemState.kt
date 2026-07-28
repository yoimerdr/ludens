package com.yoimerdr.compose.ludens.core.presentation.model.settings

import androidx.compose.runtime.Immutable
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType

/**
 * Represents an action control item state in the presentation layer.
 *
 * This is a concrete implementation of [ControlItemState] for action-type controls
 * that don't have an associated key binding. Action items represent UI control elements
 * that trigger actions for the client, instead the web view.
 *
 * @property type The [ItemType] that identifies this control item.
 * @property enabled Whether this control item is currently enabled.
 * @property alpha The opacity level of this control item (0.0 to 1.0).
 */
@Immutable
class ControlActionItemState(
    type: ItemType,
    enabled: Boolean,
    alpha: Float,
) : ControlItemState(type, enabled, alpha) {
    /**
     * Creates a copy of this control action item with optionally modified properties.
     *
     * @param type The [ItemType] for the copy. Defaults to the current type.
     * @param enabled The enabled state for the copy. Defaults to the current enabled state.
     * @param alpha The opacity level for the copy. Defaults to the current alpha value.
     * @return A new [ControlActionItemState] instance with the specified properties.
     */
    override fun copy(
        type: ItemType,
        enabled: Boolean,
        alpha: Float,
    ): ControlActionItemState = ControlActionItemState(type, enabled, alpha)
}