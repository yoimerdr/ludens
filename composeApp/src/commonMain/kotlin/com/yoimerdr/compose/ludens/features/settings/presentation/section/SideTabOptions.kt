package com.yoimerdr.compose.ludens.features.settings.presentation.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionsContainer
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SettingsEvent
import com.yoimerdr.compose.ludens.ui.components.buttons.SideTab
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.filled.ArrowLeft
import com.yoimerdr.compose.ludens.ui.icons.outlined.Apps
import com.yoimerdr.compose.ludens.ui.icons.outlined.AppsList
import com.yoimerdr.compose.ludens.ui.icons.outlined.BroadActivityFeed
import com.yoimerdr.compose.ludens.ui.icons.outlined.Games
import com.yoimerdr.compose.ludens.ui.icons.outlined.System
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.stc_tabs_about
import ludens.composeapp.generated.resources.stc_tabs_actions
import ludens.composeapp.generated.resources.stc_tabs_controls
import ludens.composeapp.generated.resources.stc_tabs_system
import ludens.composeapp.generated.resources.stc_tabs_tools
import org.jetbrains.compose.resources.stringResource

/**
 * A side tab button for selecting a settings section.
 *
 * @param selected Whether this tab is currently selected.
 * @param option The settings section this tab represents.
 * @param onClick Callback invoked when the tab is clicked.
 * @param modifier The modifier to be applied to the tab.
 */
@Composable
private fun SettingsTabOption(
    modifier: Modifier = Modifier,
    selected: Boolean,
    option: SettingsSection,
    onClick: () -> Unit,
) {
    val icon = when (option) {
        SettingsSection.Controls -> LudensIcons.Default.Games
        SettingsSection.Tools -> LudensIcons.Default.Apps
        SettingsSection.System -> LudensIcons.Default.System
        SettingsSection.About -> LudensIcons.Default.BroadActivityFeed
        SettingsSection.Actions -> LudensIcons.Default.AppsList
    }

    val text = stringResource(
        when (option) {
            SettingsSection.Controls -> Res.string.stc_tabs_controls
            SettingsSection.Tools -> Res.string.stc_tabs_tools
            SettingsSection.System -> Res.string.stc_tabs_system
            SettingsSection.About -> Res.string.stc_tabs_about
            SettingsSection.Actions -> Res.string.stc_tabs_actions
        }
    )

    SideTab(
        icon = icon,
        onClick = onClick,
        modifier = modifier,
        expanded = true,
        selected = selected
    ) {
        Text(text = text)
    }
}


/**
 * A vertical navigation panel displaying settings section tabs.
 *
 * @param section The currently selected section.
 * @param onBack Callback invoked when the back button is clicked.
 * @param onEvent Callback invoked when a section tab is selected.
 * @param modifier The modifier to be applied to the panel.
 * @param state The scroll state of the options list.
 * @param color The background color of the panel.
 */
@Composable
fun SideTabOptions(
    section: SettingsSection,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    color: Color = MaterialTheme.colorScheme.surface,
    onBack: () -> Unit,
    onEvent: (SettingsEvent.OnSelectSection) -> Unit,
) {
    val spacing = LocalSpacing.current

    Surface(
        modifier = modifier
            .sizeIn(
                maxWidth = 192.dp
            ),
        color = color
    ) {
        Column {
            Box(
                modifier = Modifier.padding(
                    horizontal = spacing.small
                )
            ) {
                IconButton(
                    onClick = onBack,
                ) {
                    Icon(
                        LudensIcons.Filled.ArrowLeft,
                        contentDescription = "Back",
                    )
                }
            }

            OptionsContainer(
                state = state,
                verticalArrangement = Arrangement.spacedBy(spacing.small),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(SettingsSection.entries) {
                    SettingsTabOption(
                        selected = section == it,
                        option = it,
                        onClick = {
                            onEvent(SettingsEvent.OnSelectSection(it))
                        }
                    )
                }
            }
        }
    }

}
