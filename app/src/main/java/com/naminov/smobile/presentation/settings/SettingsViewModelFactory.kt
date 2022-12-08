package com.naminov.smobile.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naminov.smobile.domain.usecase.settings.GetSettingsUseCase
import com.naminov.smobile.domain.usecase.settings.SaveSettingsUseCase
import javax.inject.Inject

class SettingsViewModelFactory @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(getSettingsUseCase, saveSettingsUseCase) as T
    }
}