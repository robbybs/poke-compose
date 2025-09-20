package com.rbs.pokecompose.presentation.ui.feature.detail

import com.rbs.pokecompose.domain.model.DetailPokemon

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val data: DetailPokemon, val isRefreshing: Boolean = false) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}