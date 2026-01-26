package com.yoimerdr.compose.ludens.core.di

import androidx.datastore.core.DataStore
import com.yoimerdr.compose.ludens.core.data.source.local.settings.ProtoSettings
import com.yoimerdr.compose.ludens.core.infrastructure.platform.PlatformApplication
import com.yoimerdr.compose.ludens.core.infrastructure.platform.PlatformContext
import com.yoimerdr.compose.ludens.core.presentation.platform.PlatformLocale
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.scope.Scope

internal const val dataStoreFilename = "user_preferences.pb"

expect fun Scope.createDataStore(): DataStore<ProtoSettings>

expect fun Scope.createPlatformContext(): PlatformContext

expect fun Scope.createPlatformApplication(): PlatformApplication

expect fun Scope.createPlatformLocale(): PlatformLocale

@Module
@ComponentScan("com.yoimerdr.compose.ludens.core")
@Configuration()
object CoreModule {
    private var store: DataStore<ProtoSettings>? = null

    @Single
    fun providesDataStore(scope: Scope): DataStore<ProtoSettings> {
        return store ?: scope.createDataStore().also {
            store = it
        }
    }

    @Single
    fun providesPlatformContext(scope: Scope): PlatformContext {
        return scope.createPlatformContext()
    }

    @Single
    fun providesPlatformApplication(scope: Scope): PlatformApplication {
        return scope.createPlatformApplication()
    }

    @Single
    fun providesPlatformLocale(scope: Scope): PlatformLocale {
        return scope.createPlatformLocale()
    }
}