package ludens.build.android.postbuild

import org.gradle.api.Action

/**
 * Context variables used for replacing placeholders in post-build artifact renaming patterns.
 *
 * @property appName Application display name (`{appName}`, `{name}`).
 * @property versionName Application version name (`{versionName}`, `{version}`).
 * @property versionCode Application version code (`{versionCode}`, `{code}`).
 * @property minSdk Minimum supported Android API level (`{minSdk}`).
 * @property targetSdk Target Android API level (`{targetSdk}`).
 * @property appId Android Application ID package name (`{appId}`, `{package}`).
 */
open class PostBuildContext {
    /**
     * Application display name.
     */
    var appName: String = "App"

    /**
     * Application version name.
     */
    var versionName: String = "1.0.0"

    /**
     * Application version code integer.
     */
    var versionCode: Int = 1

    /**
     * Minimum supported Android API level.
     */
    var minSdk: Int = 21

    /**
     * Target Android API level.
     */
    var targetSdk: Int = 36

    /**
     * Android Application ID package name.
     */
    var appId: String = "com.example.app"
}

/**
 * Extension object for configuring post-build Android artifact renaming and relocation.
 *
 * Registered under `ludens { android { postBuild { ... } } }`.
 *
 * Usage in `composeApp/build.gradle.kts`:
 * ```kotlin
 * ludens {
 *     android {
 *         postBuild {
 *             enable = ludensConfiguration.android.build.enable
 *             outputDir = ludensConfiguration.android.build.outputDir
 *             pattern = ludensConfiguration.android.build.pattern
 *             action = ludensConfiguration.android.build.action
 *             includeVariants = listOf(ludensConfiguration.android.build.includeVariants)

 *             context {
 *                 appName = ludensConfiguration.android.name
 *                 versionName = ludensConfiguration.android.version
 *                 versionCode = ludensConfiguration.android.versionCode
 *                 minSdk = ludensConfiguration.android.minSDK
 *                 targetSdk = ludensConfiguration.android.targetSDK
 *                 appId = ludensConfiguration.android.id
 *             }
 *         }
 *     }
 * }
 * ```
 *
 * @property enable Toggles automatic post-build artifact renaming and relocation.
 * @property outputDir Target destination directory relative to the root project.
 * @property pattern Template pattern for artifact naming (extension auto-appended).
 * @property action Operation mode: `"copy"` (duplicate) or `"move"` (relocate).
 * @property includeVariants Target build variant names (`"all"`, `"release"`, `"debug"`, etc.).
 * @property context Nested context object holding metadata placeholders.
 */
open class PostBuildExtension {
    /**
     * Whether post-build processing is enabled.
     */
    var enable: Boolean = false

    /**
     * Relative path to output directory where processed artifacts are stored.
     */
    var outputDir: String = "output/builds"

    /**
     * Template pattern used to generate output file names.
     */
    var pattern: String = "{appName}-{versionName}-{buildType}"

    /**
     * Operation mode (`"copy"` or `"move"`).
     */
    var action: String = "copy"

    /**
     * Target build variants to process.
     */
    var includeVariants: List<String> = listOf("all")

    /**
     * Nested context metadata variables used for pattern placeholder replacement.
     */
    val context: PostBuildContext = PostBuildContext()

    /**
     * Configures the [PostBuildContext] via an [Action] block.
     *
     * @param action Lambda action modifying [context].
     */
    fun context(action: Action<PostBuildContext>) {
        action.execute(context)
    }
}
