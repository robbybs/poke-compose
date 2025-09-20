package com.rbs.pokecompose.presentation.ui.feature.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbs.pokecompose.domain.usecase.GetDetailPokemonUseCase
import com.rbs.pokecompose.presentation.ui.feature.detail.DetailUiState
import kotlinx.coroutines.launch

class DetailViewModel(
    private val useCase: GetDetailPokemonUseCase
) : ViewModel() {

    var uiState by mutableStateOf<DetailUiState>(DetailUiState.Loading)
        private set

    fun getDetailPokemon(id: Int) {
        viewModelScope.launch {
            uiState = DetailUiState.Loading
            runCatching { useCase(id) }
                .onSuccess { uiState = DetailUiState.Success(it) }
                .onFailure { e ->
                    uiState = DetailUiState.Error(e.message ?: "Unknown error")
                }
        }
    }
}