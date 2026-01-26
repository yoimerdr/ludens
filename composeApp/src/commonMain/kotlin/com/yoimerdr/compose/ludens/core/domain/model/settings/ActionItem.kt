package com.yoimerdr.compose.ludens.core.domain.model.settings

/**
 * Represents a single quick action item.
 *
 * @property type The type of action this item represents.
 * @property enabled Whether this action item is enabled.
 * @property order The order of this action item.
 */
data class ActionItem(
    val type: ActionType,
    val enabled: Boolean,
    val order: Int,
)
