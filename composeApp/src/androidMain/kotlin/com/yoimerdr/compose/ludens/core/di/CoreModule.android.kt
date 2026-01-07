package com.yoimerdr.compose.ludens.core.di

import androidx.datastore.core.DataStore
import com.yoimerdr.compose.ludens.core.data.source.local.createDataStore
import com.yoimerdr.compose.ludens.core.data.source.local.settings.ProtoSettings
import com.yoimerdr.compose.ludens.core.infrastructure.platform.AndroidPlatformApplication
import com.yoimerdr.compose.ludens.core.infrastructure.platform.AndroidPlatformContext
import com.yoimerdr.compose.ludens.core.infrastructure.platform.PlatformApplication
import com.yoimerdr.compose.ludens.core.infrastructure.platform.PlatformContext
import com.yoimerdr.compose.ludens.core.presentation.platform.AndroidPlatformLocale
import com.yoimerdr.compose.ludens.core.presentation.platform.PlatformLocale
import okio.FileSystem
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope


actual fun Scope.createDataStore(): DataStore<ProtoSettings> = createDataStore(FileSystem.SYSTEM) {
    androidContext().filesDir
        .resolve(dataStoreFilename)
        .absolutePath
}

actual fun Scope.createPlatformContext(): PlatformContext {
    return AndroidPlatformContext(androidContext())
}

actual fun Scope.createPlatformApplication(): PlatformApplication =
    AndroidPlatformApplication(get())

actual fun Scope.createPlatformLocale(): PlatformLocale = AndroidPlatformLocale