package com.rbs.pokecompose.presentation.ui.feature.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbs.pokecompose.domain.usecase.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    var registerSuccess by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf("")
        private set

    fun register(name: String, username: String, password: String, confirmPassword: String) {
        if (name.isBlank() || username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            errorMessage = "All fields must not be empty"
            return
        }

        if (password != confirmPassword) {
            errorMessage = "Passwords do not match"
            return
        }

        viewModelScope.launch {
            try {
                registerUseCase(name, username, password)
                registerSuccess = true
            } catch (e: Exception) {
                errorMessage = e.message ?: "Registration failed"
            }
        }
    }
}