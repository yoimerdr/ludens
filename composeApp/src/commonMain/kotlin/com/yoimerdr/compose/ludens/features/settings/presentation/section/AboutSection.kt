package com.yoimerdr.compose.ludens.features.settings.presentation.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionCard
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionsContainer
import com.yoimerdr.compose.ludens.konfig.generated.BuildKonfig
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing
import com.yoimerdr.compose.ludens.ui.components.text.LinkText
import com.yoimerdr.compose.ludens.ui.components.text.LinkTextPart
import com.yoimerdr.compose.ludens.ui.components.text.SmallText
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.brand.Ludens
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.stc_about_description
import ludens.composeapp.generated.resources.stc_about_issues
import ludens.composeapp.generated.resources.stc_about_version
import ludens.composeapp.generated.resources.stc_about_website
import ludens.composeapp.generated.resources.stc_about_copyright
import org.jetbrains.compose.resources.stringResource

/**
 * A text component for displaying footer text in the About section.
 *
 * @param text The text content to display.
 * @param modifier The modifier to be applied to the text component.
 */
@Composable
private fun FooterText(
    text: String,
    modifier: Modifier = Modifier,
) {
    SmallText(
        text = text,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

/**
 * The header section of the About screen displaying the application logo and version.
 *
 * @param modifier The modifier to be applied to the header container.
 */
@Composable
private fun AboutHeader(
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        Icon(
            imageVector = LudensIcons.Brand.Ludens,
            contentDescription = "Ludens Logo",
            tint = Color.Unspecified
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ludens",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = stringResource(
                    Res.string.stc_about_version,
                    BuildKonfig.LUDENS_VERSION
                ),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * The footer section of the About screen displaying website links and copyright information.
 *
 * @param modifier The modifier to be applied to the footer container.
 */
@Composable
private fun AboutFooter(
    modifier: Modifier = Modifier,
) {
    val links: List<LinkTextPart> = listOf(
        LinkTextPart(
            part = stringResource(Res.string.stc_about_website),
            url = BuildKonfig.LUDENS_WEBSITE_URL
        ),
        LinkTextPart(
            part = stringResource(Res.string.stc_about_issues),
            url = BuildKonfig.LUDENS_ISSUES_URL
        )
    )


    val spacing = LocalSpacing.current
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            links.forEach {
                LinkText(
                    text = it.part,
                    url = it.url,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing.extraSmall)
        ) {
            FooterText(text = stringResource(Res.string.stc_about_description))
            FooterText(text = stringResource(Res.string.stc_about_copyright))
        }
    }
}

/**
 * The main About section displaying application information.
 *
 * @param modifier The modifier to be applied to the section container.
 * @param state The [LazyListState] to control the scroll state of the list.
 */
@Composable
fun AboutSection(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    OptionsContainer(
        modifier = modifier
            .fillMaxSize(),
        state = state,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            OptionCard(
                colors = CardDefaults.outlinedCardColors()
            ) {
                AboutHeader()

                AboutFooter()
            }
        }
    }
}
