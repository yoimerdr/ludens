package ludens.build.android.configuration

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Generates the manifest fragment consumed by the Android app module.
 *
 * The task writes a minimal `AndroidManifest.xml` containing only the enabled permissions.
 */
abstract class GeneratePermissionsManifestTask : DefaultTask() {

    /** Output manifest file that will be merged into the app manifest. */
    @get:OutputFile
    abstract val manifest: RegularFileProperty

    /** Fully qualified permission names to keep in the generated manifest fragment. */
    @get:Input
    abstract val permissions: ListProperty<String>

    @TaskAction
    fun generate() {
        // Serialize the enabled permissions into a standalone manifest fragment.
        val manifestFile = manifest.get().asFile
        val permissions = permissions.get()

        writeManifest(manifestFile, permissions)
    }

    private fun writeManifest(file: File, permissions: List<String>) {
        val sb = StringBuilder()
        sb.appendLine("""<?xml version="1.0" encoding="utf-8"?>""")
        sb.appendLine("""<manifest xmlns:android="http://schemas.android.com/apk/res/android">""")

        permissions.forEach { perm ->
            sb.appendLine("""    <uses-permission android:name="$perm" />""")
        }

        sb.appendLine("""</manifest>""")

        file.apply {
            parentFile.mkdirs()
            writeText(sb.toString())
        }
    }
}
