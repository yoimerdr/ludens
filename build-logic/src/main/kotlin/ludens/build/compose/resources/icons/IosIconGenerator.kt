/*
 * Copyright 2024 Qamar A. Safadi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ludens.build.compose.resources.icons

import org.gradle.api.logging.Logger
import java.io.File

/**
 * Specification for a single iOS icon image in the asset catalogue.
 *
 * @property pointSizeFull Display size string (e.g. `"20x20"`, `"83.5x83.5"`).
 * @property idiom Device idiom (`"iphone"`, `"ipad"`, `"ios-marketing"`).
 * @property scale Screen scale factor (`"1x"`, `"2x"`, `"3x"`).
 * @property filename Output file name (e.g. `"icon-20@2x.png"`).
 * @property actualSize Pixel dimensions of the rendered image.
 * @property role Optional semantic role (`"notification"`, `"settings"`,
 *   `"spotlight"`, `"app"`).
 * @property subtype Optional device subtype (`"pro"` for iPad Pro).
 */
data class IosIconSpec(
    val pointSizeFull: String,
    val idiom: String,
    val scale: String,
    val filename: String,
    val actualSize: Int,
    val role: String? = null,
    val subtype: String? = null,
)

/**
 * Generates iOS app icon assets from a composited source image and produces
 * the required `Contents.json` asset catalogue manifest.
 *
 * @property logger Gradle logger used for lifecycle output.
 */
class IosIconGenerator(private val logger: Logger) {

    /**
     * Complete list of iOS icon sizes required for a modern iOS app icon set,
     * covering iPhone, iPad, and App Store marketing icons.
     */
    val iconSpecs: List<IosIconSpec> = listOf(
        IosIconSpec("20x20", "iphone", "2x", "icon-20@2x.png", 40, role = "notification"),
        IosIconSpec("20x20", "iphone", "3x", "icon-20@3x.png", 60, role = "notification"),
        IosIconSpec("29x29", "iphone", "2x", "icon-29@2x.png", 58, role = "settings"),
        IosIconSpec("29x29", "iphone", "3x", "icon-29@3x.png", 87, role = "settings"),
        IosIconSpec("40x40", "iphone", "2x", "icon-40@2x.png", 80, role = "spotlight"),
        IosIconSpec("40x40", "iphone", "3x", "icon-40@3x.png", 120, role = "spotlight"),
        IosIconSpec("60x60", "iphone", "2x", "icon-60@2x.png", 120, role = "app"),
        IosIconSpec("60x60", "iphone", "3x", "icon-60@3x.png", 180, role = "app"),
        IosIconSpec("20x20", "ipad", "1x", "icon-20-ipad.png", 20, role = "notification"),
        IosIconSpec("20x20", "ipad", "2x", "icon-20@2x-ipad.png", 40, role = "notification"),
        IosIconSpec("29x29", "ipad", "1x", "icon-29-ipad.png", 29, role = "settings"),
        IosIconSpec("29x29", "ipad", "2x", "icon-29@2x-ipad.png", 58, role = "settings"),
        IosIconSpec("40x40", "ipad", "1x", "icon-40-ipad.png", 40, role = "spotlight"),
        IosIconSpec("40x40", "ipad", "2x", "icon-40@2x-ipad.png", 80, role = "spotlight"),
        IosIconSpec("76x76", "ipad", "1x", "icon-76.png", 76, role = "app"),
        IosIconSpec("76x76", "ipad", "2x", "icon-76@2x.png", 152, role = "app"),
        IosIconSpec("83.5x83.5", "ipad", "2x", "icon-83.5@2x.png", 167, role = "app", subtype = "pro"),
        IosIconSpec("1024x1024", "ios-marketing", "1x", "icon-1024.png", 1024),
    )

    /**
     * Generates the `Contents.json` manifest file for an iOS `AppIcon.appiconset`
     * asset catalogue folder.
     *
     * @param iosAppIconSetDir The target `.appiconset` directory.
     * @param iconSpecs The list of [IosIconSpec] entries to include in the manifest.
     */
    fun generateContentsJson(iosAppIconSetDir: File, iconSpecs: List<IosIconSpec>) {
        val imagesArray = iconSpecs.map { spec ->
            mutableMapOf(
                "size" to spec.pointSizeFull,
                "idiom" to spec.idiom,
                "filename" to spec.filename,
                "scale" to spec.scale,
            ).apply {
                spec.role?.let { put("role", it) }
                spec.subtype?.let { put("subtype", it) }
            }
        }
        val jsonString = StringBuilder("{\n  \"images\": [\n")
        imagesArray.forEachIndexed { index, imageMap ->
            jsonString.append("    {\n")
            imageMap.entries.forEachIndexed { entryIndex, entry ->
                val value = entry.value.replace("\"", "\\\"")
                jsonString.append(
                    "      \"${entry.key}\": \"$value\"${if (entryIndex < imageMap.entries.size - 1) "," else ""}\n"
                )
            }
            jsonString.append("    }${if (index < imagesArray.size - 1) "," else ""}\n")
        }
        jsonString.append(
            "  ],\n  \"info\": {\n    \"version\": 1,\n    \"author\": \"AppIconGenerator\"\n  }\n}\n"
        )
        File(iosAppIconSetDir, "Contents.json").writeText(jsonString.toString())
    }
}
