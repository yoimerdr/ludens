package com.yoimerdr.compose.ludens.core.di

import androidx.datastore.core.DataStore
import com.yoimerdr.compose.ludens.core.data.source.local.settings.ProtoSettings
import com.yoimerdr.compose.ludens.core.infrastructure.platform.PlatformApplication
import com.yoimerdr.compose.ludens.core.infrastructure.platform.PlatformContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.scope.Scope

internal const val dataStoreFilename = "user_preferences.pb"

expect fun Scope.createDataStore(): DataStore<ProtoSettings>

expect fun Scope.createPlatformContext(): PlatformContext

expect fun Scope.createPlatformApplication(): PlatformApplication

@Module
@ComponentScan("com.yoimerdr.compose.ludens.core")
@Configuration()
object CoreModule {
    @Single
    fun providesDataStore(scope: Scope): DataStore<ProtoSettings> {
        return scope.createDataStore()
    }

    @Single
    fun providesPlatformContext(scope: Scope): PlatformContext {
        return scope.createPlatformContext()
    }

    @Single
    fun providesPlatformApplication(scope: Scope): PlatformApplication {
        return scope.createPlatformApplication()
    }
}