package com.yoimerdr.compose.ludens.core.presentation.extension.platform

import androidx.compose.runtime.Composable
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.label
import com.yoimerdr.compose.ludens.core.presentation.platform.PlatformLocale
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.unknown_language
import org.jetbrains.compose.resources.stringResource

val PlatformLocale.label: String
    @Composable
    get() = when (val language = current) {
        SystemLanguage.System -> stringResource(Res.string.unknown_language)
        else -> language.label
    }