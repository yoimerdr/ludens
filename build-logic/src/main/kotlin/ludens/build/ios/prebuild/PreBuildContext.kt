package ludens.build.ios.prebuild

/**
 * Context variables used for populating managed keys in `Config.xcconfig`.
 *
 * @property appName Maps to `PRODUCT_NAME` in `Config.xcconfig`.
 * @property bundleId Maps to `PRODUCT_BUNDLE_IDENTIFIER` in `Config.xcconfig`.
 * @property marketingVersion Maps to `MARKETING_VERSION` in `Config.xcconfig`.
 * @property projectVersion Maps to `CURRENT_PROJECT_VERSION` in `Config.xcconfig`.
 */
open class PreBuildContext {
    /**
     * Application display name.
     */
    var appName: String = "Ludens"

    /**
     * Bundle identifier (reverse-domain).
     */
    var bundleId: String = "com.yoimerdr.compose.ludens"

    /**
     * Human-readable version string.
     */
    var marketingVersion: String = "1.0"

    /**
     * Integer build number as a string.
     */
    var projectVersion: String = "1"
}
