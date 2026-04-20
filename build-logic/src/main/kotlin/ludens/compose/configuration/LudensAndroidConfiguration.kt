package ludens.build.compose.configuration

data class LudensAndroidConfiguration(
    val id: String = "com.yoimerdr.compose.ludens",
    val version: String = "1.0",
    val name: String = "Ludens",
    val launcherName: String = "Ludens",
    val minSDK: Int = 21,
    val targetSDK: Int = 36,
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
