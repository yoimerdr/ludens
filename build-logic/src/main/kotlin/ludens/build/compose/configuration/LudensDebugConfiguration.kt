package ludens.build.compose.configuration

import java.io.Serializable

/**
 * Debugging configuration model loaded from `ludens.properties`.
 *
 * This configuration controls global application-wide diagnostic tools, logs, and tracebacks.
 */
data class LudensDebugConfiguration(
    /**
     * Toggles rich error interception and diagnostic dialog display.
     *
     * Maps from `ludens.debug.errors`.
     */
    val errors: Boolean = true,
) : Serializable
