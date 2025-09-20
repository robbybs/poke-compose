package com.rbs.pokecompose.presentation.ui.feature.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rbs.pokecompose.presentation.navigation.Routes
import com.rbs.pokecompose.presentation.ui.components.PokemonItem
import com.rbs.pokecompose.presentation.ui.feature.profile.ProfileScreen
import com.rbs.pokecompose.presentation.ui.feature.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val pokemons = viewModel.items.collectAsLazyPagingItems()
    val searchQuery by viewModel.query.collectAsState()
    val selectedTab = viewModel.selectedTab

    BackHandler(enabled = selectedTab == 1) {
        viewModel.selectTab(0)
    }

    Column {
        TopAppBar(
            title = { Text("Pokemon App") }
        )
        TabRow(selectedTabIndex = if (selectedTab == 0) 0 else 1) {
            Tab(
                selected = selectedTab == 0,
                onClick = { viewModel.selectTab(0) },
                text = { Text("Home") }
            )
            Tab(
                selected = viewModel.selectedTab == 1,
                onClick = { viewModel.selectTab(1) },
                text = { Text("Profile") }
            )
        }

        when (selectedTab) {
            0 -> {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.updateQuery(it) },
                    label = { Text("Search Pokemon") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(pokemons.itemCount) { index ->
                        pokemons[index]?.let { pokemon ->
                            PokemonItem(
                                pokemon = pokemon,
                                onClick = { pokemonId ->
                                    navController.navigate(
                                        Routes.DETAIL.replace(
                                            "{pokemonId}",
                                            pokemonId.toString()
                                        )
                                    )
                                }
                            )
                        }
                    }

                    when (pokemons.loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        is LoadState.Error -> {
                            item {
                                Text(
                                    text = "Error loading more pokemons",
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }

                        else -> {}
                    }
                }
            }

            1 -> {
                ProfileScreen(navController = navController)
            }
        }
    }
}