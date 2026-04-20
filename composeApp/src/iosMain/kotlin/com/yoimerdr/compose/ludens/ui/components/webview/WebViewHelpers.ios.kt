package com.yoimerdr.compose.ludens.ui.components.webview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.multiplatform.webview.web.PlatformWebViewParams

/**
 * Remembers iOS web view params.
 *
 * No additional client wiring is required on iOS at this layer.
 */
@Composable
internal actual fun rememberPlatformsParameters(): PlatformWebViewParams =
    remember { PlatformWebViewParams() }
