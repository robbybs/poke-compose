package com.rbs.pokecompose.presentation.ui.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rbs.pokecompose.domain.model.Pokemon
import com.rbs.pokecompose.domain.usecase.GetPokemonsUseCase
import com.rbs.pokecompose.domain.usecase.SearchPokemonUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

class HomeViewModel(
    useCase: GetPokemonsUseCase,
    private val searchUseCase: SearchPokemonUseCase
) : ViewModel() {
    var selectedTab by mutableIntStateOf(0)
        private set

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val items: Flow<PagingData<Pokemon>> =
        _query.debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { keyword ->
                if (keyword.isBlank()) {
                    useCase()
                } else {
                    searchUseCase(keyword)
                }
            }.cachedIn(viewModelScope)

    fun selectTab(tab: Int) {
        selectedTab = tab
    }
}