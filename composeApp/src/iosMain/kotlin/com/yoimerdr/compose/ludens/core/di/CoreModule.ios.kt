package com.yoimerdr.compose.ludens.core.di

import androidx.datastore.core.DataStore
import com.yoimerdr.compose.ludens.core.data.source.local.createDataStore
import com.yoimerdr.compose.ludens.core.data.source.local.settings.ProtoSettings
import com.yoimerdr.compose.ludens.core.infrastructure.platform.IOSPlatformApplication
import com.yoimerdr.compose.ludens.core.infrastructure.platform.IOSPlatformContext
import com.yoimerdr.compose.ludens.core.infrastructure.platform.PlatformApplication
import com.yoimerdr.compose.ludens.core.infrastructure.platform.PlatformContext
import kotlinx.cinterop.ExperimentalForeignApi
import okio.FileSystem
import org.koin.core.scope.Scope
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask


actual fun Scope.createDataStore(): DataStore<ProtoSettings> {

    @OptIn(ExperimentalForeignApi::class)
    val producePath = {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/$dataStoreFilename"
    }

    return createDataStore(FileSystem.SYSTEM, producePath = producePath)
}

actual fun Scope.createPlatformContext(): PlatformContext = IOSPlatformContext()
actual fun Scope.createPlatformApplication(): PlatformApplication = IOSPlatformApplication()