package ludens.build.compose.configuration

/**
 * Root configuration object for the Ludens build system.
 *
 * The expected property tree is:
 * - `ludens.android.*`
 * - `ludens.settings.*`
 *
 * Android config is consumed by the app module build script and the Android manifest generator.
 */
data class LudensConfiguration(
    val android: LudensAndroidConfiguration = LudensAndroidConfiguration(),
    val settings: LudensSettingsConfiguration = LudensSettingsConfiguration(),
)

/**
 * Wrapper used by `JavaPropsMapper` to deserialize the `ludens` root block.
 */
data class PropertiesConfiguration(
    val ludens: LudensConfiguration = LudensConfiguration(),
)
