package com.rbs.pokecompose.presentation.ui.feature.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbs.pokecompose.domain.usecase.GetDetailPokemonUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val useCase: GetDetailPokemonUseCase
) : ViewModel() {

    var uiState by mutableStateOf<DetailUiState>(DetailUiState.Loading)
        private set

    private var lastId: Int? = null

    fun getDetailPokemon(id: Int, isRefresh: Boolean = false) {
        lastId = id
        viewModelScope.launch {
            if (!isRefresh) {
                uiState = DetailUiState.Loading
            } else {
                val current = uiState
                if (current is DetailUiState.Success) {
                    uiState = current.copy(isRefreshing = true)
                }
            }

            runCatching { useCase(id) }
                .onSuccess { pokemon ->
                    uiState = DetailUiState.Success(pokemon)
                }
                .onFailure { e ->
                    uiState = DetailUiState.Error(e.message ?: "Unknown error")
                }
        }
    }

    fun retry() {
        lastId?.let { getDetailPokemon(it, isRefresh = true) }
    }
}