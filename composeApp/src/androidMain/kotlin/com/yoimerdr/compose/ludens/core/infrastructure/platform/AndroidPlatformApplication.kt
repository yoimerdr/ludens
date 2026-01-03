package com.yoimerdr.compose.ludens.core.infrastructure.platform

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import com.yoimerdr.compose.ludens.BuildConfig
import kotlin.system.exitProcess

/**
 * Android implementation of [PlatformApplication].
 *
 * Handles application lifecycle operations specific to the Android platform,
 * including proper activity termination and process cleanup.
 *
 * @property context The platform context containing the Android context information.
 */
class AndroidPlatformApplication(
    val context: PlatformContext,
) : PlatformApplication {
    companion object {
        /**
         * Recursively retrieves the Activity from a Context.
         *
         * Traverses the context hierarchy to find the underlying Activity.
         * This is useful when dealing with ContextWrapper instances.
         *
         * @return The Activity if found, null otherwise.
         */
        fun Context.getActivity(): Activity? = when (this) {
            is Activity -> this
            is ContextWrapper -> baseContext.getActivity()
            else -> null
        }
    }

    /**
     * Terminates the Android application.
     *
     * Finishes the activity, removes it from the task stack, and exits the process.
     *
     * @throws IllegalStateException if an Activity context cannot be obtained.
     */
    override fun finish() {
        val base = context as? AndroidPlatformContext
        val activity = base?.context?.getActivity()
            ?: throw IllegalStateException("Activity context is required to finish Application")
        activity.finishAndRemoveTask()
        exitProcess(0)
    }

    /**
     * Indicates whether the application is running in debug mode.
     *
     * Returns the value from Android's `BuildConfig.DEBUG`, which is `true` for
     * debug builds and `false` for release builds.
     *
     * @return `true` if the application is a debug build, `false` otherwise
     */
    override val isDebug: Boolean
        get() = BuildConfig.DEBUG
}
