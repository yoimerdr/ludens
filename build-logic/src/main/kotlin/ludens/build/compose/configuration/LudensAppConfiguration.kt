package ludens.build.compose.configuration

import java.io.Serializable

/**
 * Shared application identity loaded from `ludens.properties`.
 *
 * Values here are the cross-platform defaults consumed by both [LudensAndroidConfiguration]
 * and [LudensIosConfiguration]. Either platform block may override any of these fields
 * individually (`ludens.android.*` / `ludens.ios.*`) when its own tooling needs a value the
 * shared default can't satisfy for both platforms at once — for example a bundle identifier
 * character only one platform's rules accept.
 *
 * Validation here always applies the *stricter* of Android's and Apple's rules for each field,
 * so any value that passes here is guaranteed valid on both platforms without needing an
 * override.
 *
 * Maps from `ludens.app.*`.
 */
data class LudensAppConfiguration(
    /**
     * Shared reverse-DNS application identifier.
     *
     * Restricted to characters both Android's application id and Apple's bundle identifier
     * accept (letters, digits, dots) — no underscores (Apple disallows them) and no hyphens
     * (Android disallows them). Override per-platform via `ludens.android.id` /
     * `ludens.ios.bundleId` when a platform-specific character is required.
     *
     * Maps from `ludens.app.id`.
     */
    val id: String = "com.ludens.compose.ludens",
    /**
     * Shared display name.
     *
     * Maps from `ludens.app.name`.
     */
    val name: String = "Ludens",
    /**
     * Shared version string.
     *
     * Restricted to dot-separated non-negative integers with at least a major.minor component
     * — Apple's stricter `MARKETING_VERSION` format, which Android's own (more permissive)
     * version format always accepts too.
     *
     * Maps from `ludens.app.version`.
     */
    val version: String = "1.0",
    /**
     * Shared version code / build number.
     *
     * Maps from `ludens.app.versionCode`.
     */
    val versionCode: Int = 1,
) : Serializable {
    /**
     * This shared identity as a [LudensApplicationIdentity] — always fully populated, since
     * every field above is validated non-null/non-blank below.
     */
    val identity: LudensApplicationIdentity get() = LudensApplicationIdentity(id, name, version, versionCode)

    init {
        require(name.isNotBlank()) {
            "The shared app 'name' cannot be blank."
        }
        require(!name.contains("\$(") && !name.trimStart().startsWith("//") && !name.contains("\n")) {
            "The shared app 'name' ('$name') contains characters that would break iOS .xcconfig syntax " +
                "(a raw '\$(', a leading '//', or a newline)."
        }

        val idRegex = Regex("^[A-Za-z][A-Za-z0-9]*(\\.[A-Za-z][A-Za-z0-9]*)+$")
        require(id.matches(idRegex)) {
            "The shared app 'id' ('$id') is not valid under both Android and Apple rules " +
                "(letters, digits and dots only — no underscores, no hyphens). " +
                "Override per-platform via ludens.android.id / ludens.ios.bundleId instead."
        }

        val versionRegex = Regex("^\\d+\\.\\d+(\\.\\d+)*$")
        require(version.matches(versionRegex)) {
            "The shared app 'version' ('$version') must be dot-separated non-negative integers with at " +
                "least a major.minor component (e.g. 1.0 or 1.2.3) to be valid on both Android and iOS. " +
                "Override per-platform via ludens.android.version / ludens.ios.version instead."
        }

        require(versionCode >= 0) {
            "The shared app 'versionCode' must be non-negative. Current value: $versionCode"
        }
    }
}
