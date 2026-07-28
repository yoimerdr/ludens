package com.yoimerdr.compose.ludens.app.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Composable
fun KoinInjection(
    configuration: KoinAppDeclaration? = null,
    content: @Composable () -> Unit,
) {
    val config = remember(configuration) {
        koinConfiguration {
            configuration?.invoke(this)
            modules(ApplicationModules)
        }
    }

    KoinMultiplatformApplication(
        config = config,
        content = content
    )
}