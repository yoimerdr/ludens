package ludens.build.compose.configuration

import java.io.Serializable

/**
 * Android build configuration loaded from `ludens.properties`.
 *
 * This object controls the app identity, SDK levels, manifest flags and optional permissions
 * used by the Android application module.
 *
 * [id], [version], [versionCode] and [name] are optional overrides of the shared
 * [LudensAppConfiguration] — when unset (`null`), the Android build uses the shared
 * `ludens.app.*` value instead. Read the effective value via
 * [LudensConfiguration.androidIdentity] rather than these fields directly.
 */
data class LudensAndroidConfiguration(
    /**
     * Android application id override. Falls back to the shared `ludens.app.id` when unset.
     *
     * Maps from `ludens.android.id`.
     */
    override val id: String? = null,
    /**
     * App version name override. Falls back to the shared `ludens.app.version` when unset.
     *
     * Maps from `ludens.android.version`.
     */
    override val version: String? = null,
    /**
     * App version code override. Falls back to the shared `ludens.app.versionCode` when unset.
     *
     * Maps from `ludens.android.versionCode`.
     * */
    override val versionCode: Int? = null,
    /**
     * App display name override used in settings. Falls back to the shared `ludens.app.name`
     * when unset.
     *
     * Maps from `ludens.android.name`.
     */
    override val name: String? = null,
    /**
     * Launcher label shown under the icon.
     *
     * Maps from `ludens.android.launcherName`.
     */
    val launcherName: String = "Ludens",
    /**
     * Minimum Android API level for the app.
     *
     * Maps from `ludens.android.minSDK`.
     */
    val minSDK: Int = 21,
    /**
     * Target Android API level for the app.
     *
     * Maps from `ludens.android.targetSDK`.
     */
    val targetSDK: Int = 36,
    /**
     * Whether to enable immersive mode by default.
     *
     * Immersive means fullscreen with hidden system bars and edge-to-edge content.
     *
     * Maps from `ludens.android.immersive`.
     */
    val immersive: Boolean = true,
    /**
     * Android manifest flags.
     *
     * Maps from `ludens.android.manifest.*`.
     */
    val manifest: LudensAndroidManifestConfiguration = LudensAndroidManifestConfiguration(),
    /**
     * Optional permissions list.
     *
     * Maps from `ludens.android.permissions.*`.
     */
    val permissions: LudensAndroidPermissionsConfiguration = LudensAndroidPermissionsConfiguration(),
    /**
     * Android post-build artifact renaming and relocation configuration.
     *
     * Maps from `ludens.android.build.*`.
     */
    val build: LudensAndroidBuildConfiguration = LudensAndroidBuildConfiguration(),
) : Serializable, LudensIdentity {
    init {
        require(minSDK >= 21) {
            "minSDK must be at least 21. Current value: $minSDK"
        }
        require(targetSDK >= minSDK) {
            "targetSDK ($targetSDK) cannot be lower than minSDK ($minSDK)."
        }

        id?.let {
            val idRegex = Regex("^[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z][a-zA-Z0-9_]*)+$")
            require(it.matches(idRegex)) {
                "The 'id' override ('$it') is not a valid Android Application ID. (Valid example: com.my.app)"
            }
        }

        version?.let {
            val versionRegex = Regex("^\\d+\\.\\d+.*$")
            require(it.matches(versionRegex)) {
                "The 'version' override ('$it') is invalid. It must follow the Major.Minor format (e.g., 1.0 or 1.0.0)."
            }
        }

        name?.let {
            require(it.isNotBlank()) {
                "The 'name' override cannot be blank."
            }
        }

        require(launcherName.isNotBlank()) {
            "The 'launcherName' cannot be blank."
        }
    }
}

/**
 * Android manifest flags loaded from `ludens.properties` and passed into `AndroidManifest.xml`
 * as manifest placeholders.
 */
data class LudensAndroidManifestConfiguration(
    /**
     * Controls `android:allowBackup`.
     *
     * Maps from `ludens.android.manifest.allowBackup`.
     */
    val allowBackup: Boolean = true,
    /**
     * Controls `android:largeHeap`.
     *
     * Maps from `ludens.android.manifest.largeHeap`.
     */
    val largeHeap: Boolean = true,
    /**
     * Controls `android:hardwareAccelerated`.
     *
     * Maps from `ludens.android.manifest.hardwareAccelerated`.
     */
    val hardwareAccelerated: Boolean = true,
    /**
     * Controls `android:screenOrientation`.
     *
     * Maps from `ludens.android.manifest.screenOrientation`.
     */
    val screenOrientation: String = "sensorLandscape",
    /**
     * Controls `android:usesCleartextTraffic`.
     *
     * Maps from `ludens.android.manifest.usesCleartextTraffic`.
     */
    val usesCleartextTraffic: Boolean = false,
    /**
     * Controls `android:resizeableActivity`.
     *
     * Maps from `ludens.android.manifest.resizeableActivity`.
     */
    val resizeableActivity: Boolean = false,
) : Serializable {
    private val supportedScreenOrientations = setOf(
        "unspecified",
        "landscape",
        "portrait",
        "user",
        "behind",
        "sensor",
        "nosensor",
        "sensorLandscape",
        "sensorPortrait",
        "reverseLandscape",
        "reversePortrait",
        "fullSensor",
        "userLandscape",
        "userPortrait",
        "fullUser",
        "locked",
        "fullUserLandscape",
        "fullUserPortrait",
        "lockedLandscape",
        "lockedPortrait",
        "soloLandscape",
        "soloPortrait",
    )

    init {
        require(screenOrientation.isNotBlank()) {
            "The 'screenOrientation' cannot be blank."
        }

        require(screenOrientation in supportedScreenOrientations) {
            "The 'screenOrientation' value '$screenOrientation' is not supported. Supported values: ${
                supportedScreenOrientations.sorted().joinToString(", ")
            }."
        }

        require(!(resizeableActivity && screenOrientation == "locked")) {
            "resizeableActivity cannot be enabled when screenOrientation is locked."
        }
    }
}

/**
 * Optional Android permissions loaded from `ludens.properties`.
 *
 * `true` keeps the corresponding `<uses-permission>` entry in the generated manifest fragment.
 * `false` excludes it entirely from the generated file.
 */
data class LudensAndroidPermissionsConfiguration(
    /**
     * Toggles `android.permission.INTERNET`.
     *
     * Maps from `ludens.android.permissions.internet`.
     */
    val internet: Boolean = false,
    /**
     * Toggles `android.permission.ACCESS_NETWORK_STATE`.
     *
     * Maps from `ludens.android.permissions.networkState`.
     */
    val networkState: Boolean = false,
    /**
     * Toggles `android.permission.WAKE_LOCK`.
     *
     * Maps from `ludens.android.permissions.wakeLock`.
     */
    val wakeLock: Boolean = false,
    /**
     * Toggles `android.permission.ACCESS_WIFI_STATE`.
     *
     * Maps from `ludens.android.permissions.accessWifiState`.
     */
    val accessWifiState: Boolean = false,
    /**
     * Toggles `android.permission.CHANGE_WIFI_STATE`.
     *
     * Maps from `ludens.android.permissions.changeWifiState`.
     */
    val changeWifiState: Boolean = false,
) : Serializable

/**
 * Android build post-processing settings loaded from `ludens.properties`.
 *
 * Configures automatic renaming and relocating of compiled Android APKs and AABs.
 *
 * @property enable Whether post-build artifact renaming and relocating is enabled.
 * @property outputDir Destination directory relative to the project root.
 * @property pattern Naming template pattern (e.g. `{appName}-{versionName}-{buildType}`).
 * @property action Operation mode (`"copy"` or `"move"`).
 * @property includeVariants Target build variants (`"all"`, `"release"`, `"debug"`, or comma-separated list).
 */
data class LudensAndroidBuildConfiguration(
    val enable: Boolean = false,
    val outputDir: String = "output/builds",
    val pattern: String = "{appName}-{versionName}-{buildType}",
    val action: String = "copy",
    val includeVariants: String = "all",
) : Serializable

