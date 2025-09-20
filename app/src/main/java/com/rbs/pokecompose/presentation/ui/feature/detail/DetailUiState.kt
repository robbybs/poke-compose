package com.rbs.pokecompose.presentation.ui.feature.detail

import com.rbs.pokecompose.domain.model.DetailPokemon

sealed interface DetailUiState {
    object Loading : DetailUiState
    data class Success(val data: DetailPokemon) : DetailUiState
    data class Error(val message: String) : DetailUiState
}