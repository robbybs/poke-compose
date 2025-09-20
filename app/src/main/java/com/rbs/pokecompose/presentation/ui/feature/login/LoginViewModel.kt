package com.rbs.pokecompose.presentation.ui.feature.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbs.pokecompose.domain.usecase.GetStatusUseCase
import com.rbs.pokecompose.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel (
    private val useCase: LoginUseCase,
    private val statusUseCase: GetStatusUseCase,
) : ViewModel() {
    var loginSuccess by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf("")
        private set

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            errorMessage = "Username and password must not be empty"
            return
        }

        viewModelScope.launch {
            val user = useCase(username, password)
            if (user != null) {
                loginSuccess = true
            } else {
                errorMessage = "Invalid username or password"
            }
        }
    }

    fun checkUserSession() = viewModelScope.launch {
        val session = statusUseCase()
        loginSuccess = session.isLoggedIn
    }
}