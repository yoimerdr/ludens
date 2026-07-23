package com.yoimerdr.compose.ludens.core.infrastructure.platform

import android.content.Context

/**
 * Android implementation of [PlatformContext].
 *
 * Wraps the Android Context to provide platform-specific context information.
 *
 * @property context The Android Context instance.
 */
class AndroidPlatformContext(val context: Context) : PlatformContext