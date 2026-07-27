package ludens.build.ios.prebuild

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.GradleException
import java.io.File

/**
 * Gradle task that reads and rewrites `Config.xcconfig`, updating the four
 * managed keys (`PRODUCT_NAME`, `PRODUCT_BUNDLE_IDENTIFIER`, `MARKETING_VERSION`,
 * `CURRENT_PROJECT_VERSION`) while preserving every other line unchanged.
 *
 * Not cacheable — the task reads and rewrites the same file in place, so the
 * output is not a pure function of declared `@Input` properties (a developer
 * hand-editing `TEAM_ID` outside Gradle would not invalidate a cache hit).
 */
abstract class IosBuildPreProcessTask : DefaultTask() {

    companion object {
        private val managedKeys = setOf(
            "PRODUCT_NAME",
            "PRODUCT_BUNDLE_IDENTIFIER",
            "MARKETING_VERSION",
            "CURRENT_PROJECT_VERSION",
        )

        private val keyValueRegex = Regex("^(\\w+)\\s*=\\s*(.*)$")
    }

    /**
     * Application display name -> PRODUCT_NAME.
     */
    @get:Input
    abstract val appName: Property<String>

    /**
     * Reverse-domain bundle identifier -> PRODUCT_BUNDLE_IDENTIFIER.
     */
    @get:Input
    abstract val bundleId: Property<String>

    /**
     * Human-readable version string -> MARKETING_VERSION.
     */
    @get:Input
    abstract val marketingVersion: Property<String>

    /**
     * Integer build number as string -> CURRENT_PROJECT_VERSION.
     */
    @get:Input
    abstract val projectVersion: Property<String>

    /**
     * Target xcconfig file path.
     */
    @get:OutputFile
    abstract val targetConfigFile: RegularFileProperty

    private fun valueForKey(key: String): String = when (key) {
        "PRODUCT_NAME" -> appName.get()
        "PRODUCT_BUNDLE_IDENTIFIER" -> bundleId.get()
        "MARKETING_VERSION" -> marketingVersion.get()
        "CURRENT_PROJECT_VERSION" -> projectVersion.get()
        else -> error("Unknown managed key: $key")
    }

    private fun validateAppName(value: String) {
        require(value.isNotBlank()) {
            "iOS pre-build metadata sync: 'appName' must not be blank."
        }
        require(!value.contains("$(")) {
            "iOS pre-build metadata sync: 'appName' must not contain a raw '$(' (would corrupt xcconfig syntax). Got: '$value'"
        }
        require(!value.startsWith("//")) {
            "iOS pre-build metadata sync: 'appName' must not start with '//' (would be treated as a comment). Got: '$value'"
        }
        require(!value.contains("\n")) {
            "iOS pre-build metadata sync: 'appName' must not contain an embedded newline (would corrupt xcconfig syntax). Got: '$value'"
        }
    }

    private fun validateBundleId(value: String) {
        val segmentRegex = Regex("^[A-Za-z][A-Za-z0-9-]*$")
        val segments = value.split(".")
        require(segments.isNotEmpty() && segments.all { it.matches(segmentRegex) }) {
            "iOS pre-build metadata sync: 'bundleId' must be one or more dot-separated segments matching [A-Za-z][A-Za-z0-9-]* " +
                "(no underscores). Got: '$value'"
        }
    }

    private fun validateMarketingVersion(value: String) {
        val segmentRegex = Regex("^\\d+$")
        val segments = value.split(".")
        require(segments.isNotEmpty() && segments.all { it.matches(segmentRegex) }) {
            "iOS pre-build metadata sync: 'marketingVersion' must be one or more dot-separated non-negative integers " +
                "(e.g. '1', '1.0', '1.2.3'). Got: '$value'"
        }
    }

    private fun validateProjectVersion(value: String) {
        require(value.matches(Regex("^\\d+$"))) {
            "iOS pre-build metadata sync: 'projectVersion' must be a single non-negative integer. Got: '$value'"
        }
    }

    @TaskAction
    fun processConfig() {
        validateAppName(appName.get())
        validateBundleId(bundleId.get())
        validateMarketingVersion(marketingVersion.get())
        validateProjectVersion(projectVersion.get())

        val configFile: File = targetConfigFile.asFile.get()
        if (!configFile.exists()) {
            throw GradleException(
                "iOS pre-build metadata sync: target xcconfig file not found at expected path '$configFile'. " +
                    "Create the file or update the 'configFile' property in the preBuild DSL block."
            )
        }
        val lines = configFile.readLines()
        val seenKeys = mutableSetOf<String>()
        val outputLines = mutableListOf<String>()

        for (line in lines) {
            val match = keyValueRegex.matchEntire(line.trimEnd())
            if (match != null) {
                val key = match.groupValues[1]
                if (key in managedKeys) {
                    if (key in seenKeys) {
                        outputLines.add(line)
                    } else {
                        seenKeys.add(key)
                        outputLines.add("$key=${valueForKey(key)}")
                    }
                } else {
                    outputLines.add(line)
                }
            } else {
                outputLines.add(line)
            }
        }

        for (key in managedKeys) {
            if (key !in seenKeys) {
                outputLines.add("$key=${valueForKey(key)}")
            }
        }

        configFile.writeText(outputLines.joinToString(System.lineSeparator()) + System.lineSeparator())
    }
}
