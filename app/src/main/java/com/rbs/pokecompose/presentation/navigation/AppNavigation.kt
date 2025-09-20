package com.rbs.pokecompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rbs.pokecompose.presentation.ui.feature.detail.DetailScreen
import com.rbs.pokecompose.presentation.ui.feature.home.HomeScreen
import com.rbs.pokecompose.presentation.ui.feature.login.LoginScreen
import com.rbs.pokecompose.presentation.ui.feature.register.RegisterScreen
import com.rbs.pokecompose.presentation.ui.feature.intro.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(navController = navController)
        }
        composable(Routes.LOGIN) {
            LoginScreen(navController = navController)
        }
        composable(Routes.REGISTER) {
            RegisterScreen(navController = navController)
        }
        composable(Routes.HOME) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt("pokemonId") ?: 0
            DetailScreen(pokemonId = pokemonId)
        }
    }
}