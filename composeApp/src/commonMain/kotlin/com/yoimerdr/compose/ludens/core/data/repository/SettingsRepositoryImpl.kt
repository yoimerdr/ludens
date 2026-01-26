package com.yoimerdr.compose.ludens.core.data.repository

import com.yoimerdr.compose.ludens.core.data.source.local.SettingsStore
import com.yoimerdr.compose.ludens.core.data.mapper.settings.toDomain
import com.yoimerdr.compose.ludens.core.data.mapper.settings.toProto
import com.yoimerdr.compose.ludens.core.domain.model.settings.ControlSettings
import com.yoimerdr.compose.ludens.core.domain.model.settings.Settings
import com.yoimerdr.compose.ludens.core.domain.factory.SettingsFactory
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionSettings
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemSettings
import com.yoimerdr.compose.ludens.core.domain.model.settings.ToolSettings
import com.yoimerdr.compose.ludens.core.domain.repository.ActionSettingsFlow
import com.yoimerdr.compose.ludens.core.domain.repository.ControlSettingsFlow
import com.yoimerdr.compose.ludens.core.domain.repository.SettingsFlow
import com.yoimerdr.compose.ludens.core.domain.repository.SettingsRepository
import com.yoimerdr.compose.ludens.core.domain.repository.SystemSettingsFlow
import com.yoimerdr.compose.ludens.core.domain.repository.ToolsSettingsFlow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class SettingsRepositoryImpl(
    private val store: SettingsStore,
) : SettingsRepository {
    override fun getToolSettings(): ToolsSettingsFlow {
        return store.data.map {
            it.tool?.toDomain() ?: SettingsFactory.toolSettings()
        }
    }

    override suspend fun updateToolSettings(settings: ToolSettings) {
        store.updateData { currentSettings ->
            currentSettings.copy(tool = settings.toProto())
        }
    }

    override fun getControlSettings(): ControlSettingsFlow {
        return store.data.map { it.control?.toDomain() ?: SettingsFactory.controlSettings() }
    }

    override suspend fun updateControlSettings(settings: ControlSettings) {
        store.updateData { currentSettings ->
            currentSettings.copy(control = settings.toProto())
        }
    }

    override fun getSystemSettings(): SystemSettingsFlow {
        return store.data.map { it.system?.toDomain() ?: SettingsFactory.systemSettings() }
    }

    override suspend fun updateSystemSettings(settings: SystemSettings) {
        store.updateData { currentSettings ->
            currentSettings.copy(system = settings.toProto())
        }
    }

    override fun getActionSettings(): ActionSettingsFlow {
        return store.data.map { it.action?.toDomain() ?: SettingsFactory.actionSettings() }
    }

    override suspend fun updateActionSettings(settings: ActionSettings) {
        store.updateData { currentSettings ->
            currentSettings.copy(action = settings.toProto())
        }
    }

    override fun getSettings(): SettingsFlow {
        return store.data.map { it.toDomain() }
    }

    override suspend fun updateSettings(settings: Settings) {
        store.updateData {
            settings.toProto()
        }
    }

}

