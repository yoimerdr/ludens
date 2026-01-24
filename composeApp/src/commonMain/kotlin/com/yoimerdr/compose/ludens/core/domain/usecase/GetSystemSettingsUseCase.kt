package com.yoimerdr.compose.ludens.core.domain.usecase

import com.yoimerdr.compose.ludens.core.domain.repository.SettingsRepository
import com.yoimerdr.compose.ludens.core.domain.repository.SystemSettingsFlow
import org.koin.core.annotation.Single

/**
 * Use case for retrieving system settings.
 *
 * This use case encapsulates the business logic for fetching system settings from the repository
 * as a Flow, allowing observers to react to system settings changes.
 *
 * @property repository The settings repository for data retrieval.
 */
@Single
class GetSystemSettingsUseCase(
    private val repository: SettingsRepository,
) {
    /**
     * Retrieves the current system settings as a Flow.
     *
     * @return A Flow that emits SystemSettings whenever they change.
     */
    operator fun invoke(): SystemSettingsFlow = repository.getSystemSettings()
}

