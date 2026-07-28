package com.yoimerdr.compose.ludens.core.domain.usecase

import com.yoimerdr.compose.ludens.core.domain.repository.SettingsFlow
import com.yoimerdr.compose.ludens.core.domain.repository.SettingsRepository
import org.koin.core.annotation.Single

/**
 * Use case for retrieving application settings.
 *
 * This use case encapsulates the business logic for fetching settings from the repository
 * as a Flow, allowing observers to react to settings changes.
 *
 * @property repository The settings repository for data retrieval.
 */
@Single
class GetSettingsUseCase(
    private val repository: SettingsRepository,
) {
    /**
     * Retrieves the current settings as a Flow.
     *
     * @return A Flow that emits Settings whenever they change.
     */
    operator fun invoke(): SettingsFlow = repository.getSettings()
}