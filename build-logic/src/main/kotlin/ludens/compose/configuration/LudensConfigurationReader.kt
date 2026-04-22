package ludens.build.compose.configuration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.gradle.api.Project

/**
 * Shared `Properties` parser for Ludens build configuration.
 *
 * It reads `ludens.properties` first and falls back to `gradle.properties` only for the
 * deprecated shape. The parser accepts nested keys such as `ludens.android.id` and ignores
 * unknown keys.
 */
private val propsMapper by lazy {
    JavaPropsMapper().registerModule(kotlinModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}

/**
 * Parsed Ludens configuration for the current Gradle project.
 *
 * Root key:
 * `ludens`
 *
 * File priority:
 * 1. `ludens.properties`
 * 2. `gradle.properties`
 *
 * The returned object is used by the app module build script and the custom build-logic plugins.
 */
val Project.ludensConfiguration: LudensConfiguration
    get() {
        val ludensProp = rootProject.layout.projectDirectory.file("ludens.properties")
        val gradleProp = rootProject.layout.projectDirectory.file("gradle.properties")

        val ludensContent = providers.fileContents(ludensProp).asText
        val gradleContent = providers.fileContents(gradleProp).asText

        val contentToParse = when {
            ludensContent.isPresent -> ludensContent.get()
            gradleContent.isPresent -> gradleContent.get()
            else -> throw IllegalStateException("No configuration file found. Please create a ludens.properties or gradle.properties file.")
        }

        return propsMapper.readValue(contentToParse, PropertiesConfiguration::class.java).ludens
    }
