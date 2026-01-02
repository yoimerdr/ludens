package com.yoimerdr.compose.ludens.core.data.extension.settings

import com.yoimerdr.compose.ludens.core.data.mapper.settings.toProto
import com.yoimerdr.compose.ludens.core.data.source.local.settings.ProtoSettings
import com.yoimerdr.compose.ludens.core.domain.factory.SettingsFactory

/**
 * Extension property that provides a default instance of [ProtoSettings].
 *
 * This property uses [SettingsFactory] to create default settings and converts
 * them to the Protocol Buffer format using the [toProto] mapper function.
 *
 * @return A [ProtoSettings] instance with default values.
 */
val ProtoSettings.Companion.default: ProtoSettings
    get() = SettingsFactory.settings()
        .toProto()
