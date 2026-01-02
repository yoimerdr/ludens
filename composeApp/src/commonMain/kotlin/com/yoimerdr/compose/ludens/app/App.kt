package com.yoimerdr.compose.ludens.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.yoimerdr.compose.ludens.app.di.KoinInjection
import com.yoimerdr.compose.ludens.app.navigation.NavGraph
import com.yoimerdr.compose.ludens.app.theme.LudensTheme
import com.yoimerdr.compose.ludens.app.ui.components.BackPopup
import com.yoimerdr.compose.ludens.app.ui.providers.LocalProviders
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.dsl.KoinAppDeclaration

@Composable
fun LudensClient() {
    val nav = rememberNavController()

    LudensTheme {
        BackPopup(nav,)

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            NavGraph(nav,)
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun App(configuration: KoinAppDeclaration? = null) {
    KoinInjection(configuration) {
        LocalProviders {
            LudensClient()
        }
    }
}
