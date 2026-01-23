package com.yoimerdr.compose.ludens.core.domain.usecase

import com.yoimerdr.compose.ludens.core.domain.repository.ActionSettingsFlow
import com.yoimerdr.compose.ludens.core.domain.repository.SettingsRepository
import org.koin.core.annotation.Single

/**
 * Use case for retrieving action settings.
 *
 * This use case encapsulates the business logic for fetching action settings from the repository
 * as a Flow, allowing observers to react to action settings changes.
 *
 * @property repository The settings repository for data retrieval.
 */
@Single
class GetActionSettingsUseCase(
    private val repository: SettingsRepository,
) {
    /**
     * Retrieves the current action settings as a Flow.
     *
     * @return A Flow that emits ActionSettings whenever they change.
     */
    operator fun invoke(): ActionSettingsFlow = repository.getActionSettings()
}

