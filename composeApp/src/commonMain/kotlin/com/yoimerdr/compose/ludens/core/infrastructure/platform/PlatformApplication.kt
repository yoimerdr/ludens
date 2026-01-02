package com.yoimerdr.compose.ludens.core.infrastructure.platform

/**
 * Platform-specific application lifecycle management interface.
 *
 * Defines operations for managing the application lifecycle across different platforms
 * (Android, iOS, etc.). Provides a unified interface for common application operations.
 */
interface PlatformApplication {
    /**
     * Terminates the application.
     *
     * Performs platform-specific cleanup and exits the application gracefully.
     * The exact behavior depends on the platform implementation.
     */
    fun finish()
}