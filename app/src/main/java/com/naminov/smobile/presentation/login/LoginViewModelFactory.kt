package com.naminov.smobile.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naminov.smobile.domain.usecase.authorization.GetAuthorizationUseCase
import com.naminov.smobile.domain.usecase.login.LoginUseCase
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(
    private val getAuthorizationUseCase: GetAuthorizationUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(getAuthorizationUseCase, loginUseCase) as T
    }
}