package com.yoimerdr.compose.ludens.core.domain.usecase

import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionSettings
import com.yoimerdr.compose.ludens.core.domain.repository.SettingsRepository
import org.koin.core.annotation.Single

/**
 * Use case for updating action settings.
 *
 * This use case encapsulates the business logic for persisting updated action settings
 * to the repository.
 *
 * @property repository The settings repository for data persistence.
 */
@Single
class UpdateActionSettingsUseCase(
    private val repository: SettingsRepository,
) {
    /**
     * Updates the action settings.
     *
     * @param settings The new action settings to persist.
     */
    suspend operator fun invoke(settings: ActionSettings) {
        repository.updateActionSettings(settings)
    }
}

