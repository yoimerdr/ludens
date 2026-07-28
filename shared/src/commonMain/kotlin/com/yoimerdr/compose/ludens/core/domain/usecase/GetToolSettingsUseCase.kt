package com.yoimerdr.compose.ludens.core.domain.usecase

import com.yoimerdr.compose.ludens.core.domain.repository.SettingsRepository
import com.yoimerdr.compose.ludens.core.domain.repository.ToolsSettingsFlow
import org.koin.core.annotation.Single

/**
 * Use case for retrieving tool settings.
 *
 * This use case encapsulates the business logic for fetching tool settings from the repository
 * as a Flow, allowing observers to react to tool settings changes.
 *
 * @property repository The settings repository for data retrieval.
 */
@Single
class GetToolSettingsUseCase(
    private val repository: SettingsRepository,
) {
    /**
     * Retrieves the current tool settings as a Flow.
     *
     * @return A Flow that emits ToolSettings whenever they change.
     */
    operator fun invoke(): ToolsSettingsFlow = repository.getToolSettings()
}

