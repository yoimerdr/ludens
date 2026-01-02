package com.yoimerdr.compose.ludens.app.di

import com.yoimerdr.compose.ludens.core.di.CoreModule
import com.yoimerdr.compose.ludens.koin.generated.module
import org.koin.core.module.Module

val ApplicationModules: List<Module> = listOf(
    CoreModule.module
)