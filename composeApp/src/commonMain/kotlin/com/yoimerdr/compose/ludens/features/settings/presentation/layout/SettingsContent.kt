package com.yoimerdr.compose.ludens.features.settings.presentation.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.union
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import com.yoimerdr.compose.ludens.app.ui.providers.LocalFPSPlayer
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SettingsState
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionCard
import com.yoimerdr.compose.ludens.features.settings.presentation.secction.AboutSection
import com.yoimerdr.compose.ludens.features.settings.presentation.secction.ControlsSettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.secction.SideTabOptions
import com.yoimerdr.compose.ludens.features.settings.presentation.secction.SystemSettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.secction.ToolsSettings
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsSection
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing
import com.yoimerdr.compose.ludens.ui.state.PluginState
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState
import com.yoimerdr.compose.ludens.ui.state.isAvailable

/**
 * The main settings content layout displaying the side navigation and selected section.
 *
 * @param section The currently selected settings section.
 * @param language The current system language.
 * @param settings The current settings state.
 * @param features The current web features state.
 * @param plugin The current plugin state.
 * @param onClose Callback invoked when the settings screen is closed.
 * @param onEvent Callback invoked when a settings event occurs.
 */
@Composable
fun SettingsContents(
    settings: SettingsState,
    features: WebFeaturesState,
    plugin: PluginState,
    onClose: () -> Unit,
    section: SettingsSection = SettingsSection.Controls,
    language: SystemLanguage = SystemLanguage.System,
    onEvent: (SettingsEvent) -> Unit,
) {
    val counter = LocalFPSPlayer.current
    val spacing = LocalSpacing.current
    val color = MaterialTheme.colorScheme.surfaceContainerLowest

    val insets = WindowInsets.safeGestures
    val verticalPadding = insets
        .only(WindowInsetsSides.Top)
        .union(
            WindowInsets(
                top = spacing.medium,
            )
        )
        .asPaddingValues()

    val horizontalPadding = insets
        .only(WindowInsetsSides.Horizontal)
        .union(
            WindowInsets(
                left = spacing.medium,
                right = spacing.medium
            )
        )
        .asPaddingValues()

    LaunchedEffect(plugin.isLoading) {
        if (plugin.isAvailable) {
            onEvent(SettingsEvent.UpdateShowFps(counter.isVisible))
        }
    }

    OptionCard(
        shape = RectangleShape,
        padding = PaddingValues.Zero,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontalPadding)
                    .padding(verticalPadding)
                    .fillMaxHeight(),
            ) {
                SideTabOptions(
                    section = section,
                    onBack = onClose,
                    onEvent = onEvent,
                    language = language
                )
            }

            Box(
                modifier = Modifier
                    .background(color)
                    .fillMaxHeight()
                    .padding(horizontalPadding)
                    .padding(verticalPadding)
            ) {
                when (section) {
                    SettingsSection.Controls -> {
                        ControlsSettingsSection(
                            settings = settings.control,
                            onEvent = onEvent,
                        )
                    }

                    SettingsSection.Tools -> {
                        ToolsSettings(
                            features = features,
                            settings = settings.tool,
                            plugin = plugin,
                            onEvent = onEvent,
                        )
                    }

                    SettingsSection.System -> {
                        SystemSettingsSection(
                            settings = settings.system,
                            onEvent = onEvent,
                        )
                    }

                    SettingsSection.About -> {
                        AboutSection(

                        )
                    }

                    else -> {}
                }
            }

        }

    }

}