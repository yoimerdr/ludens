package com.yoimerdr.compose.ludens.core.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.yoimerdr.compose.ludens.core.data.source.local.settings.ProtoSettings
import okio.FileSystem
import okio.Path.Companion.toPath


fun createDataStore(
    fileSystem: FileSystem,
    producePath: () -> String,
): DataStore<ProtoSettings> = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = fileSystem,
        producePath = { producePath().toPath() },
        serializer = SettingsSerializer,
    ),
)