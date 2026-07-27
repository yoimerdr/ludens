package ludens.build

import org.gradle.api.Project
import javax.inject.Inject

/**
 * iOS-specific sub-extension exposed through [LudensExtension.ios].
 *
 * This extension serves as the configuration entry point for iOS build
 * plugin customizations.
 *
 * @property project The Gradle project this extension is attached to.
 */
abstract class LudensIosExtension @Inject constructor(internal val project: Project)
