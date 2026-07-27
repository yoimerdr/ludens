package ludens.build.compose.configuration

import java.io.Serializable

/**
 * Root Ludens build configuration loaded from `ludens.properties`.
 *
 * This is the canonical in-memory model consumed by the custom build-logic plugins.
 * It groups the Android, settings, language and font configuration blocks under a single
 * `ludens` root key.
 *
 * Example shape:
 * ```properties
 * ludens.android.id=com.example.game
 * ludens.settings.presetName=recommended
 * ludens.languages.available=*
 * ludens.fonts.display=plusjakartasans.ttf
 * ```
 *
 * Every nested block has safe defaults, so a project can omit most keys and still build.
 */
data class LudensConfiguration(
    /**
     * Shared cross-platform application identity (id, name, version, version code).
     *
     * [android] and [ios] may each override any of these individually; use the `resolved*`
     * properties below to read the effective value for a given platform.
     *
     * Loaded from the `ludens.app.*` namespace.
     */
    val app: LudensAppConfiguration = LudensAppConfiguration(),
    /**
     * Android-specific build and manifest settings.
     *
     * Loaded from the `ludens.android.*` namespace.
     */
    val android: LudensAndroidConfiguration = LudensAndroidConfiguration(),
    /**
     * iOS-specific pre-build metadata sync settings.
     *
     * Loaded from the `ludens.ios.*` namespace.
     */
    val ios: LudensIosConfiguration = LudensIosConfiguration(),
    /**
     * Settings preset selection and raw override values.
     *
     * Loaded from the `ludens.settings.*` namespace.
     */
    val settings: LudensSettingsConfiguration = LudensSettingsConfiguration(),
    /**
     * Language selection rules used by generated resources.
     *
     * Loaded from the `ludens.languages.*` namespace.
     */
    val languages: LudensLanguageConfiguration = LudensLanguageConfiguration(),
    /**
     * Compose font defaults and per-language fallbacks.
     *
     * Loaded from the `ludens.fonts.*` namespace.
     */
    val fonts: LudensFontConfiguration = LudensFontConfiguration(),
    /**
     * App icon generator settings.
     *
     * Loaded from the `ludens.icons.*` namespace.
     */
    val icons: LudensIconConfiguration = LudensIconConfiguration(),
    /**
     * Application debugging and tracing settings.
     *
     * Loaded from the `ludens.debug.*` namespace.
     */
    val debug: LudensDebugConfiguration = LudensDebugConfiguration(),
) : Serializable {
    /**
     * Effective Android identity — [android]'s override resolved against the shared [app]
     * identity.
     */
    val androidIdentity: LudensApplicationIdentity get() = android.resolve(app.identity)

    /**
     * Effective iOS identity — [ios]'s override resolved against the shared [app] identity.
     * [LudensApplicationIdentity.id] is the effective bundle identifier.
     */
    val iosIdentity: LudensApplicationIdentity get() = ios.resolve(app.identity)
}

/**
 * Jackson wrapper used by `JavaPropsMapper` to deserialize the `ludens` root block.
 *
 * `JavaPropsMapper` expects a top-level property name that matches the root object in the
 * properties file. This wrapper keeps the public configuration model clean while still
 * matching the serialized structure.
 */
data class PropertiesConfiguration(
    /**
     * Root Ludens configuration node expected by the properties parser.
     */
    val ludens: LudensConfiguration = LudensConfiguration(),
) : Serializable
