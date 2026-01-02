package com.yoimerdr.compose.ludens.core.infrastructure.platform

import platform.posix.exit

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
}