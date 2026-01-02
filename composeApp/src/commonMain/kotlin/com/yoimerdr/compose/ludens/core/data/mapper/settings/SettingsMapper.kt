package com.yoimerdr.compose.ludens.core.data.mapper.settings

import com.yoimerdr.compose.ludens.core.data.source.local.settings.ProtoControlItem
import com.yoimerdr.compose.ludens.core.data.source.local.settings.ProtoControlSettings
import com.yoimerdr.compose.ludens.core.data.source.local.settings.ProtoPositionableItem
import com.yoimerdr.compose.ludens.core.data.source.local.settings.ProtoSettings
import com.yoimerdr.compose.ludens.core.data.source.local.settings.ProtoToolSettings
import com.yoimerdr.compose.ludens.core.domain.factory.SettingsFactory
import com.yoimerdr.compose.ludens.core.domain.model.settings.ControlItem
import com.yoimerdr.compose.ludens.core.domain.model.settings.ControlSettings
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableItem
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableType
import com.yoimerdr.compose.ludens.core.domain.model.settings.Settings
import com.yoimerdr.compose.ludens.core.domain.model.settings.ToolSettings
import com.yoimerdr.compose.ludens.core.domain.value.Alpha

fun Settings.toProto(): ProtoSettings = ProtoSettings(
    tool = tool.toProto(), control = control.toProto(), positions = positions.map { it.toProto() })

fun ToolSettings.toProto(): ProtoToolSettings = ProtoToolSettings(
    isMuted = isMuted, showFPS = showFPS
)

fun ControlSettings.toProto(): ProtoControlSettings = ProtoControlSettings(
    enabled = enabled, alpha = alpha.value, items = items.map { it.toProto() })

fun ControlItem.toProto(): ProtoControlItem = ProtoControlItem(
    type = type.value, enabled = enabled, alpha = alpha.value, key = code ?: 0
)

fun PositionableItem.toProto(): ProtoPositionableItem = ProtoPositionableItem(
    type = type.value, x = x, y = y
)

fun ProtoSettings.toDomain(): Settings = Settings(
    tool = tool?.toDomain() ?: SettingsFactory.toolSettings(),
    control = control?.toDomain() ?: SettingsFactory.controlSettings(),
    positions = positions.map { it.toDomain() })

fun ProtoToolSettings.toDomain(): ToolSettings = ToolSettings(
    isMuted = isMuted, showFPS = showFPS
)

fun ProtoControlSettings.toDomain(): ControlSettings = ControlSettings(
    enabled = enabled, alpha = Alpha.coerce(alpha), items = items.map { it.toDomain() })

fun ProtoControlItem.toDomain(): ControlItem = ControlItem(
    type = ItemType.from(type),
    enabled = enabled,
    alpha = Alpha.coerce(alpha),
    code = key
)

fun ProtoPositionableItem.toDomain(): PositionableItem = PositionableItem(
    type = PositionableType.from(type), x = x, y = y
)
