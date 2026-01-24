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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionCard
import com.yoimerdr.compose.ludens.features.settings.presentation.secction.AboutSection
import com.yoimerdr.compose.ludens.features.settings.presentation.secction.ActionSettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.secction.ControlsSettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.secction.SideTabOptions
import com.yoimerdr.compose.ludens.features.settings.presentation.secction.SystemSettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.secction.ToolsSettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.ActionSettingsViewModel
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.ControlsSettingsViewModel
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.RootSettingsViewModel
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.SystemSettingsViewModel
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.ToolsSettingsViewModel
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing
import org.koin.compose.viewmodel.koinViewModel

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
    viewModel: RootSettingsViewModel = koinViewModel(),
    controlsViewModel: ControlsSettingsViewModel = koinViewModel(),
    toolsViewModel: ToolsSettingsViewModel = koinViewModel(),
    systemViewModel: SystemSettingsViewModel = koinViewModel(),
    actionViewModel: ActionSettingsViewModel = koinViewModel(),
) {
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

    OptionCard(
        shape = RectangleShape,
        padding = PaddingValues.Zero,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        SettingsContents(
            viewModel = viewModel,
            controlsViewModel = controlsViewModel,
            toolsViewModel = toolsViewModel,
            systemViewModel = systemViewModel,
            actionViewModel = actionViewModel,
            horizontalPadding = horizontalPadding,
            verticalPadding = verticalPadding,
            color = color,
            onClose = onClose
        )
    }

}

/**
 * Internal settings content layout with precalculated padding values.
 *
 * @param viewModel The settings view model.
 * @param horizontalPadding The horizontal padding values.
 * @param verticalPadding The vertical padding values.
 * @param color The background color for the content area.
 * @param onClose Callback invoked when the settings screen is closed.
 */
@Composable
private fun SettingsContents(
    viewModel: RootSettingsViewModel,
    controlsViewModel: ControlsSettingsViewModel,
    toolsViewModel: ToolsSettingsViewModel,
    systemViewModel: SystemSettingsViewModel,
    actionViewModel: ActionSettingsViewModel,
    horizontalPadding: PaddingValues,
    verticalPadding: PaddingValues,
    color: Color,
    onClose: () -> Unit,
) {
    val section = viewModel.section

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
                onEvent = viewModel::handle,
                onBack = onClose
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

                else -> {}
            }
        }

    }
}