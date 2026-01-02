package com.yoimerdr.compose.ludens.core.domain.usecase

import com.yoimerdr.compose.ludens.core.domain.model.settings.Settings
import com.yoimerdr.compose.ludens.core.domain.repository.SettingsRepository
import org.koin.core.annotation.Factory

/**
 * Use case for updating application settings.
 *
 * This use case encapsulates the business logic for persisting updated settings
 * to the repository.
 *
 * @property repository The settings repository for data persistence.
 */
@Factory
class UpdateSettingsUseCase(private val repository: SettingsRepository,
) {
    /**
     * Updates the application settings.
     *
     * @param settings The new settings to persist.
     */
    suspend operator fun invoke(settings: Settings) {
        repository.updateSettings(settings)
    }
}