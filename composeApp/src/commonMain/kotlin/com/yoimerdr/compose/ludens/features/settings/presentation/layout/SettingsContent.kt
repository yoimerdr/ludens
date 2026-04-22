package com.yoimerdr.compose.ludens.features.settings.presentation.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.features.settings.presentation.section.AboutSection
import com.yoimerdr.compose.ludens.features.settings.presentation.section.ActionSettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.section.ControlsSettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.section.SideTabOptions
import com.yoimerdr.compose.ludens.features.settings.presentation.section.SystemSettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.section.ToolsSettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.ActionSettingsViewModel
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.ControlsSettingsViewModel
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.RootSettingsViewModel
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.SystemSettingsViewModel
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.ToolsSettingsViewModel
import com.yoimerdr.compose.ludens.ui.components.layout.ResponsiveBox
import com.yoimerdr.compose.ludens.ui.components.provider.LocalBreakpoints
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing

/**
 * The main settings content layout displaying the side navigation and selected section.
 *
 * @param onClose Callback invoked when the settings screen is closed.
 * @param viewModel The root settings view model managing navigation.
 * @param controlsViewModel The controls settings view model.
 * @param toolsViewModel The tools settings view model.
 * @param systemViewModel The system settings view model.
 * @param actionViewModel The action settings view model.
 */
@Composable
fun SettingsContents(
    onClose: () -> Unit,
    viewModel: RootSettingsViewModel,
    controlsViewModel: ControlsSettingsViewModel,
    toolsViewModel: ToolsSettingsViewModel,
    systemViewModel: SystemSettingsViewModel,
    actionViewModel: ActionSettingsViewModel,
) {
    val spacing = LocalSpacing.current
    val breakpoints = LocalBreakpoints.current
    val contentColor = MaterialTheme.colorScheme.surfaceContainerLowest

    val insets = WindowInsets.safeGestures
    val padding = insets
        .union(
            WindowInsets(
                top = spacing.medium,
                bottom = spacing.medium,
                left = spacing.medium,
                right = spacing.medium
            )
        )
        .asPaddingValues()

    val railWidth = 192.dp
    val shellGap = spacing.large
    val contentMaxWidth = 980.dp

    ResponsiveBox(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        compact = {
            SettingsSectionContent(
                centered = false,
                compact = true,
                contentColor = contentColor,
                padding = padding,
                viewModel = viewModel,
                controlsViewModel = controlsViewModel,
                toolsViewModel = toolsViewModel,
                systemViewModel = systemViewModel,
                actionViewModel = actionViewModel,
                contentMaxWidth = contentMaxWidth,
                shellGap = shellGap,
                onClose = onClose,
            )
        },
        medium = {
            SettingsSectionContent(
                centered = maxWidth >= breakpoints.extraLarge,
                compact = false,
                contentColor = contentColor,
                padding = padding,
                viewModel = viewModel,
                controlsViewModel = controlsViewModel,
                toolsViewModel = toolsViewModel,
                systemViewModel = systemViewModel,
                actionViewModel = actionViewModel,
                contentMaxWidth = contentMaxWidth,
                railWidth = railWidth,
                shellGap = shellGap,
                onClose = onClose,
            )
        },
    )
}

@Composable
private fun CompactSettingsContent(
    contentColor: Color,
    viewModel: RootSettingsViewModel,
    controlsViewModel: ControlsSettingsViewModel,
    toolsViewModel: ToolsSettingsViewModel,
    systemViewModel: SystemSettingsViewModel,
    actionViewModel: ActionSettingsViewModel,
    contentMaxWidth: Dp = 980.dp,
    shellGap: Dp = LocalSpacing.current.large,
    onClose: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = contentMaxWidth),
        verticalArrangement = Arrangement.spacedBy(shellGap),
    ) {
        SideTabOptions(
            section = viewModel.section,
            vertical = false,
            onEvent = viewModel::handle,
            onBack = onClose,
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = contentColor,
            shape = MaterialTheme.shapes.large,
            shadowElevation = 0.dp,
        ) {
            SettingsSectionContent(
                viewModel = viewModel,
                section = viewModel.section,
                controlsViewModel = controlsViewModel,
                toolsViewModel = toolsViewModel,
                systemViewModel = systemViewModel,
                actionViewModel = actionViewModel,
            )
        }
    }
}

@Composable
private fun ExpandedSettingsContent(
    centered: Boolean,
    contentColor: Color,
    viewModel: RootSettingsViewModel,
    controlsViewModel: ControlsSettingsViewModel,
    toolsViewModel: ToolsSettingsViewModel,
    systemViewModel: SystemSettingsViewModel,
    actionViewModel: ActionSettingsViewModel,
    contentMaxWidth: Dp = 980.dp,
    railWidth: Dp = 192.dp,
    shellGap: Dp = LocalSpacing.current.large,
    onClose: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val shellMaxWidth: Dp = railWidth + shellGap + contentMaxWidth

    Row(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = if (centered) shellMaxWidth else Dp.Unspecified),
        horizontalArrangement = Arrangement.spacedBy(shellGap),
        verticalAlignment = Alignment.Top,
    ) {
        SideTabOptions(
            modifier = Modifier.width(railWidth)
                .fillMaxHeight(),
            section = viewModel.section,
            vertical = true,
            onEvent = viewModel::handle,
            onBack = onClose,
        )

        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .widthIn(max = contentMaxWidth),
            color = contentColor,
            shadowElevation = 0.dp,
            shape = MaterialTheme.shapes.large,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacing.medium)
            ) {
                SettingsSectionContent(
                    viewModel = viewModel,
                    section = viewModel.section,
                    controlsViewModel = controlsViewModel,
                    toolsViewModel = toolsViewModel,
                    systemViewModel = systemViewModel,
                    actionViewModel = actionViewModel,
                )
            }
        }
    }
}

@Composable
private fun SettingsSectionContent(
    centered: Boolean,
    compact: Boolean,
    contentColor: Color,
    padding: PaddingValues,
    viewModel: RootSettingsViewModel,
    controlsViewModel: ControlsSettingsViewModel,
    toolsViewModel: ToolsSettingsViewModel,
    systemViewModel: SystemSettingsViewModel,
    actionViewModel: ActionSettingsViewModel,
    contentMaxWidth: Dp = 980.dp,
    railWidth: Dp = 192.dp,
    shellGap: Dp = LocalSpacing.current.large,
    onClose: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = if (centered) Alignment.TopCenter else Alignment.TopStart,
    ) {
        if (compact) {
            CompactSettingsContent(
                contentColor = contentColor,
                viewModel = viewModel,
                controlsViewModel = controlsViewModel,
                toolsViewModel = toolsViewModel,
                systemViewModel = systemViewModel,
                actionViewModel = actionViewModel,
                contentMaxWidth = contentMaxWidth,
                shellGap = shellGap,
                onClose = onClose,
            )
        } else {
            ExpandedSettingsContent(
                centered = centered,
                contentColor = contentColor,
                viewModel = viewModel,
                controlsViewModel = controlsViewModel,
                toolsViewModel = toolsViewModel,
                systemViewModel = systemViewModel,
                actionViewModel = actionViewModel,
                contentMaxWidth = contentMaxWidth,
                railWidth = railWidth,
                shellGap = shellGap,
                onClose = onClose,
            )
        }
    }
}

/**
 * Renders the currently selected settings section without altering its behavior.
 *
 * @param viewModel The settings view model.
 */
@Composable
private fun SettingsSectionContent(
    viewModel: RootSettingsViewModel,
    controlsViewModel: ControlsSettingsViewModel,
    toolsViewModel: ToolsSettingsViewModel,
    systemViewModel: SystemSettingsViewModel,
    actionViewModel: ActionSettingsViewModel,
    section: SettingsSection,
) {
    when (section) {
        SettingsSection.Controls -> {
            ControlsSettingsSection(
                viewModel = controlsViewModel
            )
        }

        SettingsSection.Tools -> {
            ToolsSettingsSection(
                viewModel = toolsViewModel,
                onNavigate = { destination ->
                    viewModel.handle(SettingsEvent.NavigateTo(destination))
                }
            )
        }

        SettingsSection.System -> {
            SystemSettingsSection(
                viewModel = systemViewModel
            )
        }

        SettingsSection.About -> {
            AboutSection()
        }

        SettingsSection.Actions -> {
            ActionSettingsSection(
                viewModel = actionViewModel
            )
        }
    }
}
