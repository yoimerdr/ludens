package com.yoimerdr.compose.ludens.core.presentation.model.settings

import androidx.compose.runtime.Immutable
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionType

/**
 * Represents a single quick action item state in the presentation layer.
 *
 * This immutable data class holds the configuration for a quick action
 * that can be triggered by the user to toggle various application settings.
 *
 * @property type The [ActionType] that identifies this action item.
 * @property enabled Whether this action item is currently enabled and interactive.
 * @property order The display order of this action item in the UI.
 */
@Immutable
data class ActionItemState(
    val enabled: Boolean,
    val type: ActionType,
    val order: Int,
)

