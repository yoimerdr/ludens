package ludens.build.compose.configuration

/**
 * Per-platform identity override — same property names as [LudensApplicationIdentity], any of
 * which may be `null` ("no override, use the shared default").
 *
 * [LudensAndroidConfiguration] and [LudensIosConfiguration] implement this directly on their
 * own constructor properties (`override val id`, etc.) — no renaming needed except where a
 * platform's field is legitimately named differently (`bundleId` on iOS).
 */
interface LudensIdentity {
    val id: String?
    val name: String?
    val version: String?
    val versionCode: Int?
}

/**
 * Fully resolved application identity — every field guaranteed non-null. Used both as the
 * shared default's own shape ([LudensAppConfiguration.identity]) and as the result of
 * [LudensIdentity.resolve].
 */
data class LudensApplicationIdentity(
    val id: String,
    val name: String,
    val version: String,
    val versionCode: Int,
)

/**
 * Resolves this override against the [shared] app identity: each field wins when set
 * (non-null); [shared]'s value is used otherwise. Always fully non-null — no assertion needed
 * by callers.
 */
fun LudensIdentity.resolve(shared: LudensApplicationIdentity): LudensApplicationIdentity =
    LudensApplicationIdentity(
        id = id ?: shared.id,
        name = name ?: shared.name,
        version = version ?: shared.version,
        versionCode = versionCode ?: shared.versionCode,
    )
