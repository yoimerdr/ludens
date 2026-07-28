package com.yoimerdr.compose.ludens.core.domain.usecase

import com.yoimerdr.compose.ludens.core.domain.model.settings.ToolSettings
import com.yoimerdr.compose.ludens.core.domain.repository.SettingsRepository
import org.koin.core.annotation.Single

/**
 * Use case for updating tool settings.
 *
 * This use case encapsulates the business logic for persisting updated tool settings
 * to the repository.
 *
 * @property repository The settings repository for data persistence.
 */
@Single
class UpdateToolSettingsUseCase(
    private val repository: SettingsRepository,
) {
    /**
     * Updates the tool settings.
     *
     * @param settings The new tool settings to persist.
     */
    suspend operator fun invoke(settings: ToolSettings) {
        repository.updateToolSettings(settings)
    }
}

