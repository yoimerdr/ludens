package com.yoimerdr.compose.ludens.core.domain.usecase

import com.yoimerdr.compose.ludens.core.domain.repository.ControlSettingsFlow
import com.yoimerdr.compose.ludens.core.domain.repository.SettingsRepository
import org.koin.core.annotation.Single

/**
 * Use case for retrieving control settings.
 *
 * This use case encapsulates the business logic for fetching control settings from the repository
 * as a Flow, allowing observers to react to control settings changes.
 *
 * @property repository The settings repository for data retrieval.
 */
@Single
class GetControlSettingsUseCase(
    private val repository: SettingsRepository,
) {
    /**
     * Retrieves the current control settings as a Flow.
     *
     * @return A Flow that emits ControlSettings whenever they change.
     */
    operator fun invoke(): ControlSettingsFlow = repository.getControlSettings()
}

