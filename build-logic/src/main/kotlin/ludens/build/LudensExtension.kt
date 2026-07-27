package ludens.build

import org.gradle.api.Action
import org.gradle.api.Project
import javax.inject.Inject

/**
 * Top-level Gradle extension registered by [LudensBuildPlugin] under the `ludens` name.
 *
 * Provides three sub-extensions for configuring different aspects of the build:
 * - [compose] — for Compose Multiplatform related plugins.
 * - [android] — for Android related plugins.
 * - [ios] — for iOS related plugins.
 *
 * **Note:** These entry points are organized by convention rather than strict restriction.
 * While an Android-affecting plugin *could* technically be activated via the [compose] extension,
 * it is not the ideal structural practice.
 *
 * The extension is designed to be used from a module's `build.gradle.kts` via
 * a DSL block:
 * ```kotlin
 * ludens {
 *     compose {
 *         // Compose-specific configuration
 *     }
 *     android {
 *         // Android-specific configuration
 *     }
 *     ios {
 *         // iOS-specific configuration
 *     }
 * }
 * ```
 *
 * @property compose The Compose sub-extension.
 * @property android The Android sub-extension.
 * @property ios The iOS sub-extension.
 */
abstract class LudensExtension @Inject constructor(project: Project) {
    val compose: LudensComposeExtension = project.objects.newInstance(LudensComposeExtension::class.java, project)

    val android: LudensAndroidExtension = project.objects.newInstance(LudensAndroidExtension::class.java, project)

    val ios: LudensIosExtension = project.objects.newInstance(LudensIosExtension::class.java, project)

    /**
     * Configures the [LudensComposeExtension] via an [Action] block.
     */
    fun compose(action: Action<LudensComposeExtension>) {
        action.execute(compose)
    }

    /**
     * Configures the [LudensAndroidExtension] via an [Action] block.
     */
    fun android(action: Action<LudensAndroidExtension>) {
        action.execute(android)
    }

    /**
     * Configures the [LudensIosExtension] via an [Action] block.
     */
    fun ios(action: Action<LudensIosExtension>) {
        action.execute(ios)
    }
}