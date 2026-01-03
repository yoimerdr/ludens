package com.yoimerdr.compose.ludens.core.infrastructure.platform

import platform.posix.exit
import kotlin.experimental.ExperimentalNativeApi

/**
 * iOS implementation of [PlatformApplication].
 *
 * Handles application lifecycle operations specific to the iOS platform.
 */
class IOSPlatformApplication : PlatformApplication {
    /**
     * Terminates the iOS application.
     *
     * Exits the application process with status code 0.
     */
    override fun finish() {
        exit(0)
    }

    /**
     * Indicates whether the application is running in debug mode.
     *
     * Uses Kotlin's experimental native API to determine if the binary
     * was compiled in debug mode.
     *
     * @return `true` if the application is a debug build, `false` otherwise
     */
    @OptIn(ExperimentalNativeApi::class)
    override val isDebug: Boolean
        get() = Platform.isDebugBinary
}