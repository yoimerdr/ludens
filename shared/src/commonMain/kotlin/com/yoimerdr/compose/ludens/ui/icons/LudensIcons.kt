package com.yoimerdr.compose.ludens.ui.icons

import com.yoimerdr.compose.ludens.ui.icons.LudensIcons.Default


/**
 * Central object for accessing icon collections.
 *
 * Provides organized access to different icon styles used throughout the application.
 *
 * Use [Default] to access the default icon style, which currently points to [Outlined].
 */
object LudensIcons {
    /**
     * Collection of outlined style icons.
     */
    object Outlined

    /**
     * Collection of filled style icons.
     */
    object Filled

    /**
     * Collection of brand-specific icons.
     *
     * Contains icons related to external brands, services, or platform-specific elements.
     */
    object Brand

    /**
     * Default icon collection to use throughout the application.
     */
    val Default = Outlined
}