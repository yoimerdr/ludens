package ludens.build.compose.configuration

/**
 * Android build configuration loaded from `ludens.properties`.
 *
 * This object controls the app identity, SDK levels, manifest flags and optional permissions
 * used by the Android application module.
 */
data class LudensAndroidConfiguration(
    /**
     * Android application id.
     *
     * Maps from `ludens.android.id`.
     */
    val id: String = "com.yoimerdr.compose.ludens",
    /**
     * App version name.
     *
     * Maps from `ludens.android.version`.
     */
    val version: String = "1.0",
    /**
     * App version code.
     *
     * Maps from `ludens.android.versionCode`.
     * */
    val versionCode: Int = 1,
    /**
     * App display name used in settings.
     *
     * Maps from `ludens.android.name`.
     */
    val name: String = "Ludens",
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
) {
    init {
        require(minSDK >= 21) {
            "minSDK must be at least 21. Current value: $minSDK"
        }
        require(targetSDK >= minSDK) {
            "targetSDK ($targetSDK) cannot be lower than minSDK ($minSDK)."
        }

        val idRegex = Regex("^[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z][a-zA-Z0-9_]*)+$")
        require(id.matches(idRegex)) {
            "The id '$id' is not a valid Android Application ID. (Valid example: com.my.app)"
        }

        val versionRegex = Regex("^\\d+\\.\\d+.*$")
        require(version.matches(versionRegex)) {
            "The version '$version' is invalid. It must follow the Major.Minor format (e.g., 1.0 or 1.0.0)."
        }

        require(name.isNotBlank()) {
            "The app 'name' cannot be blank."
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
) {
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
)
