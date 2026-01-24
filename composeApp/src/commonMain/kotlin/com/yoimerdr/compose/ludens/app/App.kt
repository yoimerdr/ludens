package com.yoimerdr.compose.ludens.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yoimerdr.compose.ludens.app.di.KoinInjection
import com.yoimerdr.compose.ludens.app.navigation.NavGraph
import com.yoimerdr.compose.ludens.app.theme.LudensTheme
import com.yoimerdr.compose.ludens.app.ui.components.BackPopup
import com.yoimerdr.compose.ludens.app.ui.providers.LocalProviders
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.isDarkTheme
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.SystemSettingsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.dsl.KoinAppDeclaration

@Composable
fun LudensClient(
    language: SystemLanguage? = null,
    nav: NavHostController = rememberNavController(),
    systemViewModel: SystemSettingsViewModel = koinInject(),
) {
    BackPopup(nav)

    LocalProviders(
        language = language
    ) {
        NavGraph(
            navController = nav,
            systemViewModel = systemViewModel
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun App(configuration: KoinAppDeclaration? = null) {
    KoinInjection(configuration) {
        val settingsViewModel: SystemSettingsViewModel = koinInject()

        val state by settingsViewModel.state.collectAsStateWithLifecycle()

        LudensTheme(
            darkTheme = state.theme.isDarkTheme
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) {
                LudensClient(
                    language = state.language,
                    systemViewModel = settingsViewModel
                )
            }
        }
    }
}
