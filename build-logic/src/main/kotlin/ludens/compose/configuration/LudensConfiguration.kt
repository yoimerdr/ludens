package ludens.build.compose.configuration

data class LudensConfiguration(
    val android: LudensAndroidConfiguration = LudensAndroidConfiguration(),
    val settings: LudensSettingsConfiguration = LudensSettingsConfiguration(),
)

data class PropertiesConfiguration(
    val ludens: LudensConfiguration = LudensConfiguration(),
)
