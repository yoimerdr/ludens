package ludens.build.compose.configuration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.gradle.api.Project

private val propsMapper by lazy {
    JavaPropsMapper().registerModule(kotlinModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}

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
