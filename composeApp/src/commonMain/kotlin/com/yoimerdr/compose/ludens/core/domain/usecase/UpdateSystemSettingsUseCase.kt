package com.yoimerdr.compose.ludens.core.domain.usecase

import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemSettings
import com.yoimerdr.compose.ludens.core.domain.repository.SettingsRepository
import org.koin.core.annotation.Single

/**
 * Use case for updating system settings.
 *
 * This use case encapsulates the business logic for persisting updated system settings
 * to the repository.
 *
 * @property repository The settings repository for data persistence.
 */
@Single
class UpdateSystemSettingsUseCase(
    private val repository: SettingsRepository,
) {
    /**
     * Updates the system settings.
     *
     * @param settings The new system settings to persist.
     */
    suspend operator fun invoke(settings: SystemSettings) {
        repository.updateSystemSettings(settings)
    }
}

