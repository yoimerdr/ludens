package com.yoimerdr.compose.ludens.ui.components.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A composable that provides a safe area for its content, avoiding system gestures and display cutouts.
 *
 * @param modifier The modifier to be applied to the component.
 * @param fullBackgroundContent A composable to be displayed as the background, filling the entire screen.
 * @param fullForegroundContent A composable to be displayed as the foreground, filling the entire screen.
 * @param content The main content to be displayed within the safe area.
 */
@Composable
fun SafeContent(
    modifier: Modifier = Modifier,
    fullBackgroundContent: @Composable (BoxScope.() -> Unit)? = null,
    fullForegroundContent: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        fullBackgroundContent?.invoke(this)

        Box(
            modifier = Modifier.fillMaxSize()
                .displayCutoutPadding()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .safeGesturesPadding(),
                content = content
            )
        }

        fullForegroundContent?.invoke(this)
    }
}