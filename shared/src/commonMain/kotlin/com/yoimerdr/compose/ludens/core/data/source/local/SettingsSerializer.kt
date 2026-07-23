package com.yoimerdr.compose.ludens.core.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.core.okio.OkioSerializer
import com.yoimerdr.compose.ludens.core.data.extension.settings.default
import com.yoimerdr.compose.ludens.core.data.source.local.settings.ProtoSettings
import okio.BufferedSink
import okio.BufferedSource

typealias SettingsStore = DataStore<ProtoSettings>

object SettingsSerializer : OkioSerializer<ProtoSettings> {
    override val defaultValue: ProtoSettings
        get() = ProtoSettings.default

    override suspend fun readFrom(source: BufferedSource): ProtoSettings {
        return try {
            ProtoSettings.ADAPTER.decode(source)
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(
        t: ProtoSettings,
        sink: BufferedSink,
    ) {
        sink.write(t.encode())
    }
}