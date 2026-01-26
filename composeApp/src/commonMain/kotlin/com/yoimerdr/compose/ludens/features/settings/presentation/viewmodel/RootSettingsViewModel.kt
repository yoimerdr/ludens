package com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.yoimerdr.compose.ludens.core.domain.usecase.GetSettingsUseCase
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toUIModel
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SettingsEvent
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SettingsState as SettingsStateModel

/**
 * Root ViewModel for managing settings screen navigation and read-only settings state.
 *
 * @param getSettingsUseCase Use case for retrieving read-only settings.
 */
@KoinViewModel
class RootSettingsViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
) : BaseSettingsViewModel<SettingsStateModel>(SettingsStateModel()) {

    var section by mutableStateOf(SettingsSection.Controls)
        private set


    init {
        viewModelScope.launch {
            getSettingsUseCase().stateCollect {
                it.toUIModel()
            }
        }
    }

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnSelectSection -> {
                section = event.section
            }

            else -> {}
        }
    }

    /**
     * Whether the application requires a restart due to settings changes.
     *
     * Returns true if tool settings (mute or WebGL) have changed from their original values.
     */
    val requireRestart: Boolean
        get() {
            val current = settings
            val source = sourceSettings ?: return false
            val tool = current.tool
            val sourceTool = source.tool
            return tool.isMuted != sourceTool.isMuted || tool.useWebGL != sourceTool.useWebGL
        }
}

