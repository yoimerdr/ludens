package ludens.build.compose.configuration

import java.io.Serializable

/**
 * iOS build configuration loaded from `ludens.properties`.
 *
 * [bundleId], [name], [version] and [versionCode] are optional overrides of the shared
 * [LudensAppConfiguration] — when unset (`null`), the iOS pre-build sync uses the shared
 * `ludens.app.*` value instead. Read the effective value via
 * [LudensConfiguration.iosIdentity] rather than these fields directly.
 *
 * Loaded from the `ludens.ios.*` namespace.
 */
data class LudensIosConfiguration(
    /**
     * iOS bundle identifier override. Falls back to the shared `ludens.app.id` when unset.
     *
     * Use this when the shared id uses a character Apple's tooling accepts but doesn't need to
     * (or vice versa) — the shared default is already restricted to characters both platforms
     * accept, so this is only needed for a genuinely platform-specific identifier.
     *
     * Maps from `ludens.ios.bundleId`.
     */
    val bundleId: String? = null,
    /**
     * iOS display name override. Falls back to the shared `ludens.app.name` when unset.
     *
     * Maps from `ludens.ios.name`.
     */
    override val name: String? = null,
    /**
     * iOS marketing version override. Falls back to the shared `ludens.app.version` when unset.
     *
     * Maps from `ludens.ios.version`.
     */
    override val version: String? = null,
    /**
     * iOS build number override. Falls back to the shared `ludens.app.versionCode` when unset.
     *
     * Maps from `ludens.ios.versionCode`.
     */
    override val versionCode: Int? = null,
    /**
     * iOS pre-build metadata sync settings.
     *
     * Maps from `ludens.ios.build.*`.
     */
    val build: LudensIosBuildConfiguration = LudensIosBuildConfiguration(),
) : Serializable, LudensIdentity {
    /**
     * [LudensIdentity.id] — mapped from [bundleId], iOS's own name for this field.
     */
    override val id: String? get() = bundleId

    init {
        bundleId?.let {
            val segmentRegex = Regex("^[A-Za-z][A-Za-z0-9-]*$")
            val segments = it.split(".")
            require(segments.isNotEmpty() && segments.all { segment -> segment.matches(segmentRegex) }) {
                "The 'bundleId' override ('$it') must be one or more dot-separated segments matching " +
                    "[A-Za-z][A-Za-z0-9-]* (no underscores)."
            }
        }

        version?.let {
            val segmentRegex = Regex("^\\d+$")
            val segments = it.split(".")
            require(segments.isNotEmpty() && segments.all { segment -> segment.matches(segmentRegex) }) {
                "The 'version' override ('$it') must be one or more dot-separated non-negative integers " +
                    "(e.g. '1', '1.0', '1.2.3')."
            }
        }

        name?.let {
            require(it.isNotBlank()) {
                "The 'name' override cannot be blank."
            }
        }

        versionCode?.let {
            require(it >= 0) {
                "The 'versionCode' override must be non-negative. Current value: $it"
            }
        }
    }
}

/**
 * iOS pre-build sync settings loaded from `ludens.properties`.
 *
 * @property enable Whether the iOS pre-build metadata sync is enabled.
 */
data class LudensIosBuildConfiguration(
    val enable: Boolean = false,
) : Serializable
