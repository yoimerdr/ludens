package ludens.build.ios.prebuild

import org.gradle.api.Action

/**
 * Extension object for configuring iOS pre-build metadata sync.
 *
 * Registered under `ludens { ios { preBuild { ... } } }`.
 *
 * @property enable Toggles automatic iOS metadata sync.
 * @property configFile Path to `Config.xcconfig` relative to the root project.
 * @property context Nested context object holding xcconfig value sources.
 */
open class PreBuildExtension {
    /**
     * Whether iOS pre-build metadata sync is enabled.
     */
    var enable: Boolean = false

    /**
     * Relative path to the target xcconfig file from the root project.
     */
    var configFile: String = "iosApp/Configuration/Config.xcconfig"

    /**
     * Nested context metadata variables used to populate xcconfig managed keys.
     */
    val context: PreBuildContext = PreBuildContext()

    /**
     * Configures the [PreBuildContext] via an [Action] block.
     *
     * @param action Lambda action modifying [context].
     */
    fun context(action: Action<PreBuildContext>) {
        action.execute(context)
    }
}
