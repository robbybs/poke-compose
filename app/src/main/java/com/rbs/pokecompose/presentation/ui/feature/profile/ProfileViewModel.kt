package com.rbs.pokecompose.presentation.ui.feature.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbs.pokecompose.domain.model.User
import com.rbs.pokecompose.domain.usecase.GetStatusUseCase
import com.rbs.pokecompose.domain.usecase.GetUserUseCase
import com.rbs.pokecompose.domain.usecase.LogoutUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val statusUseCase: GetStatusUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    var user by mutableStateOf<User?>(null)
        private set

    var isLoggedOut by mutableStateOf(false)
        private set

    init {
        loadUser()
    }

    private fun loadUser() = viewModelScope.launch {
        try {
            val session = statusUseCase()
            Log.d("ProfileVM", "Session: $session")

            if (session.isLoggedIn) {
                user = getUserUseCase(session.username)
                Log.d("ProfileVM", "User loaded: $user")
            } else {
                Log.d("ProfileVM", "User not logged in")
                user = null
            }
        } catch (e: Exception) {
            Log.e("ProfileVM", "Error loading user", e)
            user = null
        }
    }

    fun refreshUser() {
        loadUser()
    }

    fun logout() = viewModelScope.launch {
        try {
            logoutUseCase()
            isLoggedOut = true
            Log.d("ProfileVM", "Logout success")
        } catch (e: Exception) {
            Log.e("ProfileVM", "Logout failed", e)
        }
    }
}