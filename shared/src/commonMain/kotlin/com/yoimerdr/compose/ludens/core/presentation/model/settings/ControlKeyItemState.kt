package com.yoimerdr.compose.ludens.core.presentation.model.settings

import androidx.compose.runtime.Immutable
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.InputKey

/**
 * Represents a key-bound control item state in the presentation layer.
 *
 * This is a specialized implementation of [ControlKeyboardItemState] for controls that are
 * associated with keyboard input. Each instance represents a mappable control that
 * triggers when a specific [InputKey] is pressed.
 */
@Immutable
class ControlInputKeyItemState(
    type: ItemType,
    enabled: Boolean,
    alpha: Float,
    key: InputKey,
) : ControlKeyboardItemState<InputKey>(type, enabled, alpha, key) {

    override fun copy(
        key: InputKey,
        type: ItemType,
        enabled: Boolean,
        alpha: Float,
    ): ControlInputKeyItemState = ControlInputKeyItemState(type, enabled, alpha, key)
}

/**
 * A type alias for [ControlInputKeyItemState] to maintain compatibility with other modules.
 * */
typealias ControlKeyItemState = ControlInputKeyItemState