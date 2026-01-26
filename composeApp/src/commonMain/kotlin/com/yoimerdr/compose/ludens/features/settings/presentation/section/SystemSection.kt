package com.yoimerdr.compose.ludens.features.settings.presentation.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemTheme
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.label
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SystemSettingsState
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionCard
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionName
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionsContainer
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.OnChangeLanguage
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.OnChangeTheme
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.RestoreDefaultSettings
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateAudioMuted
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateUseWebGL
import com.yoimerdr.compose.ludens.features.settings.presentation.state.requests.RequestMute
import com.yoimerdr.compose.ludens.features.settings.presentation.state.requests.RequestWebGL
import com.yoimerdr.compose.ludens.features.settings.presentation.state.requests.SettingsRequest
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.SystemSettingsViewModel
import com.yoimerdr.compose.ludens.ui.components.buttons.FilledTonalToggleButton
import com.yoimerdr.compose.ludens.ui.components.provider.CollectInteractionResult
import com.yoimerdr.compose.ludens.ui.components.provider.LocalInteractionManager
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.PhoneDesktop
import com.yoimerdr.compose.ludens.ui.icons.outlined.WeatherMoon
import com.yoimerdr.compose.ludens.ui.icons.outlined.WeatherSunny
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.change
import ludens.composeapp.generated.resources.reset
import ludens.composeapp.generated.resources.stc_system_appearance
import ludens.composeapp.generated.resources.stc_system_language
import ludens.composeapp.generated.resources.stc_system_reset_default
import org.jetbrains.compose.resources.stringResource

/**
 * A button to reset all settings to default values.
 *
 * @param onEvent Callback invoked when the reset button is clicked.
 */
@Composable
private fun ResetAction(
    onEvent: (RestoreDefaultSettings) -> Unit,
) {
    OptionCard(
        colors = CardDefaults.outlinedCardColors(),
        prefix = {
            OptionName(
                text = stringResource(Res.string.stc_system_reset_default)
            )
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        FilledTonalToggleButton(
            onClick = {
                onEvent(RestoreDefaultSettings)
            },
        ) {
            Text(
                text = stringResource(Res.string.reset)
            )
        }
    }
}

/**
 * A toggle button for selecting a theme option.
 *
 * @param theme The theme this option represents.
 * @param selected Whether this theme is currently selected.
 * @param onClick Callback invoked when the option is clicked.
 * @param modifier The modifier to be applied to the button.
 */
@Composable
private fun ThemeOption(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    theme: SystemTheme,
    onClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val imageVector = when (theme) {
        SystemTheme.Light -> LudensIcons.Default.WeatherSunny
        SystemTheme.Dark -> LudensIcons.Default.WeatherMoon
        SystemTheme.System -> LudensIcons.Default.PhoneDesktop
    }

    val name = theme.label
    val iconDescription: String? = null

    FilledTonalToggleButton(
        onClick = onClick,
        modifier = modifier,
        selected = selected,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = iconDescription,
            )

            Text(
                name,
            )
        }
    }
}

/**
 * A card displaying theme selection options.
 *
 * @param theme The currently selected theme.
 * @param onEvent Callback invoked when a theme is selected.
 */
@Composable
private fun AppearanceAction(
    theme: SystemTheme,
    onEvent: (OnChangeTheme) -> Unit,
) {
    val spacing = LocalSpacing.current
    OptionCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors()
    ) {
        OptionName(
            text = stringResource(Res.string.stc_system_appearance)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(spacing.small),
            verticalArrangement = Arrangement.spacedBy(spacing.small),
            maxItemsInEachRow = 3,
            modifier = Modifier.fillMaxWidth(),
            itemVerticalAlignment = Alignment.CenterVertically
        ) {
            SystemTheme.entries.forEach {
                ThemeOption(
                    modifier = Modifier
                        .padding(spacing.extraSmall)
                        .sizeIn(minWidth = 120.dp)
                        .weight(1f),
                    theme = it,
                    selected = it == theme,
                    onClick = {
                        onEvent(OnChangeTheme(it))
                    }
                )
            }
        }
    }
}

/**
 * A card displaying language selection dropdown.
 *
 * @param language The currently selected language.
 * @param items The set of available languages.
 * @param onEvent Callback invoked when a language is selected.
 */
@Composable
private fun LanguageAction(
    language: SystemLanguage,
    items: Set<SystemLanguage>,
    onEvent: (OnChangeLanguage) -> Unit,
) {
    var language by remember(language) { mutableStateOf(language) }
    var expanded by remember { mutableStateOf(false) }

    OptionCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors(),
        prefix = {
            OptionName(
                text = stringResource(Res.string.stc_system_language)
            )
        }
    ) {
        Box {
            FilledTonalToggleButton(
                onClick = {
                    expanded = true
                }
            ) {
                Text(
                    text = stringResource(Res.string.change)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.wrapContentWidth()
            ) {
                items.forEach {
                    DropdownMenuItem(
                        text = { Text(it.label) },
                        onClick = {
                            expanded = false
                            language = it
                            onEvent(OnChangeLanguage(it))
                        },
                        enabled = language != it
                    )
                }
            }
        }
    }
}

/**
 * The system settings section displaying system configuration options.
 *
 * @param settings The current system settings state.
 * @param onEvent Callback invoked when a settings event occurs.
 * @param modifier The modifier to be applied to the section container.
 * @param state The scroll state of the options list.
 */
@Composable
fun SystemSettingsSection(
    modifier: Modifier = Modifier,
    settings: SystemSettingsState,
    state: LazyListState = rememberLazyListState(),
    onEvent: (SettingsEvent) -> Unit,
) {
    OptionsContainer(
        modifier = modifier,
        state = state,
    ) {
        item {
            AppearanceAction(
                theme = settings.theme,
                onEvent = onEvent
            )
        }

        item {
            LanguageAction(
                language = settings.language,
                items = SystemLanguage.entries.toSet(),
                onEvent = onEvent
            )
        }

        item {
            ResetAction(onEvent)
        }
    }
}

/**
 * The system settings section with view model integration.
 *
 * @param viewModel The system settings view model.
 * @param modifier The modifier to be applied to the section container.
 * @param state The scroll state of the options list.
 */
@Composable
fun SystemSettingsSection(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    viewModel: SystemSettingsViewModel,
) {
    val system by viewModel.state.collectAsStateWithLifecycle()
    val interactionManager = LocalInteractionManager.current

    CollectInteractionResult(
        onReject = {
            if (it.request is SettingsRequest)
                viewModel.reject(it.request)
        }
    ) {
        if (it.request is SettingsRequest)
            viewModel.resolve(it.request)
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is UpdateAudioMuted -> {
                    interactionManager.request(RequestMute(it.enabled))
                }

                is UpdateUseWebGL -> {
                    interactionManager.request(RequestWebGL(it.enabled))
                }

                else -> {}
            }
        }
    }

    SystemSettingsSection(
        modifier = modifier,
        settings = system,
        state = state,
        onEvent = viewModel::handle
    )
}