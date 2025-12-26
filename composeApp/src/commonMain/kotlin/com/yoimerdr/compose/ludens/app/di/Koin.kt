package com.yoimerdr.compose.ludens.app.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.KoinApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.Module
import org.koin.dsl.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Composable
fun KoinInjection(
    additionalModules: (KoinApplication.() -> List<Module>)? = null,
    content: @Composable () -> Unit,
) {
    val configuration = remember(additionalModules) {
        koinConfiguration {
            modules(ApplicationModules + (additionalModules?.invoke(this) ?: emptyList()))
        }
    }

    KoinMultiplatformApplication(
        config = configuration,
        content = content
    )
}