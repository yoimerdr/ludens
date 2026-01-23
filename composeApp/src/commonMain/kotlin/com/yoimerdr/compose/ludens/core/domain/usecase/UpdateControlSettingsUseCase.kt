package com.yoimerdr.compose.ludens.core.domain.usecase

import com.yoimerdr.compose.ludens.core.domain.model.settings.ControlSettings
import com.yoimerdr.compose.ludens.core.domain.repository.SettingsRepository
import org.koin.core.annotation.Single

/**
 * Use case for updating control settings.
 *
 * This use case encapsulates the business logic for persisting updated control settings
 * to the repository.
 *
 * @property repository The settings repository for data persistence.
 */
@Single
class UpdateControlSettingsUseCase(
    private val repository: SettingsRepository,
) {
    /**
     * Updates the control settings.
     *
     * @param settings The new control settings to persist.
     */
    suspend operator fun invoke(settings: ControlSettings) {
        repository.updateControlSettings(settings)
    }
}

