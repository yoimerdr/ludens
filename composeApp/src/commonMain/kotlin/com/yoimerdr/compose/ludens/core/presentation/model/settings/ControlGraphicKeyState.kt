package com.yoimerdr.compose.ludens.core.presentation.model.settings

import androidx.compose.runtime.Immutable
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.GraphicsKey


/**
 * Represents a key-bound control item state in the presentation layer.
 *
 * This is a specialized implementation of [ControlKeyboardItemState] for controls that are
 * associated with keyboard input. Each instance represents a mappable control that
 * triggers when a specific [GraphicsKey] is pressed.
 */
@Immutable
class ControlGraphicKeyState(
    type: ItemType,
    enabled: Boolean,
    alpha: Float,
    key: GraphicsKey,
) : ControlKeyboardItemState<GraphicsKey>(type, enabled, alpha, key) {
    override fun copy(
        key: GraphicsKey,
        type: ItemType,
        enabled: Boolean,
        alpha: Float,
    ): ControlKeyboardItemState<GraphicsKey> = ControlGraphicKeyState(type, enabled, alpha, key)
}