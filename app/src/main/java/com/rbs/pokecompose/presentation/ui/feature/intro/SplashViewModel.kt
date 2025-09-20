package com.rbs.pokecompose.presentation.ui.feature.intro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbs.pokecompose.domain.usecase.GetStatusUseCase
import com.rbs.pokecompose.presentation.navigation.Routes
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getStatusUseCase: GetStatusUseCase
) : ViewModel() {

    var isReady by mutableStateOf(false)
        private set

    var startDestination by mutableStateOf(Routes.LOGIN)
        private set

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() = viewModelScope.launch {
        val session = getStatusUseCase()
        startDestination = if (session.isLoggedIn) {
            Routes.HOME
        } else {
            Routes.LOGIN
        }
        isReady = true
    }
}