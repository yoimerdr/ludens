package com.yoimerdr.compose.ludens.core.presentation.mapper.settings

import com.yoimerdr.compose.ludens.core.domain.model.settings.ControlItem
import com.yoimerdr.compose.ludens.core.domain.model.settings.ControlSettings
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableItem
import com.yoimerdr.compose.ludens.core.domain.model.settings.Settings
import com.yoimerdr.compose.ludens.core.domain.model.settings.ToolSettings
import com.yoimerdr.compose.ludens.core.domain.value.Alpha
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.InputKey
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlActionItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlKeyItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.PositionableItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ToolSettingsState

fun Settings.toUIModel(): SettingsState = SettingsState(
    tool = tool.toUIModel(),
    control = control.toUIModel(),
    theme = theme
)

fun ToolSettings.toUIModel(): ToolSettingsState = ToolSettingsState(
    isMuted = isMuted, showFPS = showFPS
)

fun ControlSettings.toUIModel(): ControlSettingsState = ControlSettingsState(
    enabled = enabled,
    alpha = alpha.value,
    items = items.map { it.toUIModel() },
    positions = positions.map { it.toUIModel() }
)

/**
 * Converts domain [ControlItem] to presentation layer [ControlItemState].
 *
 * This function determines whether to create a [ControlKeyItemState] or
 * [ControlActionItemState] based on whether the item has a key code and
 * its type is in the keys category.
 *
 * @return A [ControlItemState] representation of this domain control item.
 */
fun ControlItem.toUIModel(): ControlItemState {
    val action = ControlActionItemState(type, enabled, alpha.value)
    if (code == null)
        return action
    val key = InputKey.from(code) ?: return action

    return when (type) {
        in ItemType.keys -> ControlKeyItemState(
            type, enabled, alpha.value, key
        )

        else -> action
    }
}

fun PositionableItem.toUIModel(): PositionableItemState = PositionableItemState(
    type = type, x = x, y = y
)

fun SettingsState.toDomain(): Settings = Settings(
    tool = tool.toDomain(),
    control = control.toDomain(),
    theme = theme
)

fun ToolSettingsState.toDomain(): ToolSettings = ToolSettings(
    isMuted = isMuted, showFPS = showFPS
)

fun ControlSettingsState.toDomain(): ControlSettings = ControlSettings(
    enabled = enabled,
    alpha = Alpha.coerce(alpha),
    items = items.map { it.toDomain() },
    positions = positions.map { it.toDomain() }
)

/**
 * Converts presentation layer [ControlItemState] to domain [ControlItem].
 *
 * This function extracts the key code from [ControlKeyItemState] instances
 * and creates the appropriate domain model.
 *
 * @return A [ControlItem] domain representation of this UI state.
 */
fun ControlItemState.toDomain(): ControlItem {
    val item = ControlItem(
        type = type,
        enabled = enabled,
        alpha = Alpha.coerce(alpha)
    )

    return when (this) {
        is ControlKeyItemState -> item.copy(code = key.code)
        else -> item
    }
}

fun PositionableItemState.toDomain(): PositionableItem = PositionableItem(
    type = type, x = x, y = y
)
