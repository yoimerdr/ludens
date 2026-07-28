package com.yoimerdr.compose.ludens.app.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.app.theme.LudensTheme
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionCard
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionName
import com.yoimerdr.compose.ludens.ui.components.buttons.FilledTonalToggleButton
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.Apps
import com.yoimerdr.compose.ludens.ui.icons.outlined.Games
import com.yoimerdr.compose.ludens.ui.icons.outlined.Settings
import com.yoimerdr.compose.ludens.ui.icons.outlined.TopSpeed
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Main design preview screen showcasing Ludens UI components and color tokens.
 *
 * Renders a scrollable layout with four sections:
 * 1. Hero billboard with the design philosophy headline.
 * 2. Component showcase using production-ready [OptionCard] composables.
 * 3. Color token visualization of surface container levels.
 * 4. Call-to-action banner.
 *
 * A top bar toggles between light and dark themes in real time.
 */
@Composable
private fun DesignPreviewScreen() {
    var isDark by remember { mutableStateOf(false) }

    LudensTheme(darkTheme = isDark) {
        val spacing = LocalSpacing.current

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                Surface(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(horizontal = spacing.medium, vertical = spacing.small)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "LUDENS / DESIGN",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )

                        FilledTonalToggleButton(
                            onClick = { isDark = !isDark },
                            selected = isDark
                        ) {
                            Text(if (isDark) "Night Mode" else "Day Mode")
                        }
                    }
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(spacing.extraLarge),
                contentPadding = PaddingValues(bottom = spacing.extraLarge)
            ) {
                // 1. HERO BILLBOARD
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.medium, vertical = spacing.extraLarge),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "The art of\ninvisible UI.",
                            style = MaterialTheme.typography.displayLarge,
                            lineHeight = MaterialTheme.typography.displayLarge.fontSize * 1.1f
                        )
                        Spacer(modifier = Modifier.height(spacing.medium))
                        Text(
                            text = "A binary rhythm system built for RPG Maker porting.",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // 2. COMPONENT SHOWCASE (Cards over background)
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.medium),
                        verticalArrangement = Arrangement.spacedBy(spacing.medium)
                    ) {
                        SectionHeader("COMPONENTS", "Production-ready option cards")

                        OptionCard(
                            title = "Game Engine",
                            icon = LudensIcons.Outlined.Games,
                            iconDescription = null
                        ) {
                            Text("MZ/MV", color = MaterialTheme.colorScheme.primary)
                        }

                        OptionCard(
                            title = "Performance Mode",
                            icon = LudensIcons.Outlined.TopSpeed,
                            iconDescription = null
                        ) {
                            Switch(checked = true, onCheckedChange = {})
                        }

                        OptionCard(
                            prefix = {
                                Column {
                                    OptionName(text = "Advanced Config")
                                    Text(
                                        text = "Subtle hierarchy with Inter body",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            },
                            suffix = {
                                Button(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.secondary,
                                        contentColor = MaterialTheme.colorScheme.onSecondary
                                    ),
                                    shape = MaterialTheme.shapes.medium
                                ) {
                                    Icon(
                                        imageVector = LudensIcons.Outlined.Settings,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp),
                                        tint = MaterialTheme.colorScheme.onSecondary
                                    )
                                    Spacer(Modifier.width(spacing.small))
                                    Text("Setup")
                                }
                            }
                        )
                    }
                }

                // 3. COLOR TOKENS (Surface container visualization)
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            .padding(vertical = spacing.extraLarge, horizontal = spacing.medium),
                        verticalArrangement = Arrangement.spacedBy(spacing.medium)
                    ) {
                        SectionHeader("COLOR RHYTHM", "Surface container levels in Dark/Light")

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(spacing.small)
                        ) {
                            TokenBox(
                                "Lowest",
                                MaterialTheme.colorScheme.surfaceContainerLowest,
                                Modifier.weight(1f)
                            )
                            TokenBox(
                                "Low",
                                MaterialTheme.colorScheme.surfaceContainerLow,
                                Modifier.weight(1f)
                            )
                            TokenBox(
                                "High",
                                MaterialTheme.colorScheme.surfaceContainerHigh,
                                Modifier.weight(1f)
                            )
                            TokenBox(
                                "Highest",
                                MaterialTheme.colorScheme.surfaceContainerHighest,
                                Modifier.weight(1f)
                            )
                        }
                    }
                }

                // 4. CALL TO ACTION
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(spacing.medium)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.shapes.large
                            )
                            .padding(spacing.extraLarge),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                LudensIcons.Outlined.Apps,
                                null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(Modifier.height(spacing.medium))
                            Text(
                                "Ready to Build",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Section header for a preview group.
 *
 * @param title Short uppercase label (displayed in primary color).
 * @param subtitle Descriptive headline beneath the title.
 */
@Composable
private fun SectionHeader(title: String, subtitle: String) {
    val spacing = LocalSpacing.current
    Column(modifier = Modifier.padding(bottom = spacing.small)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * Small square color swatch with a label underneath.
 *
 * @param label Text displayed below the swatch.
 * @param color Fill color for the square.
 * @param modifier Modifier for sizing and layout.
 */
@Composable
private fun TokenBox(label: String, color: Color, modifier: Modifier) {
    val spacing = LocalSpacing.current
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .background(color, MaterialTheme.shapes.medium)
        )
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = spacing.extraSmall)
        )
    }
}

/**
 * Preview entry point rendered for ludens design showcase.
 *
 * Wraps [DesignPreviewScreen] inside [LudensTheme] with the user-selected
 */
@Preview
@Composable
fun LudensDesignPreview() {
    DesignPreviewScreen()
}
