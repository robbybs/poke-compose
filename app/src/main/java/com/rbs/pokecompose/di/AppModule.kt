package com.rbs.pokecompose.di

import androidx.room.Room
import com.rbs.pokecompose.data.local.PokeDatabase
import com.rbs.pokecompose.data.local.UserPreferences
import com.rbs.pokecompose.data.remote.apiservice.PokemonApiService
import com.rbs.pokecompose.data.repository.PokemonRepoImpl
import com.rbs.pokecompose.data.repository.UserRepoImpl
import com.rbs.pokecompose.domain.repository.PokemonRepository
import com.rbs.pokecompose.domain.repository.UserRepository
import com.rbs.pokecompose.domain.usecase.GetDetailPokemonUseCase
import com.rbs.pokecompose.domain.usecase.GetStatusUseCase
import com.rbs.pokecompose.domain.usecase.GetPokemonsUseCase
import com.rbs.pokecompose.domain.usecase.GetUserUseCase
import com.rbs.pokecompose.domain.usecase.LoginUseCase
import com.rbs.pokecompose.domain.usecase.LogoutUseCase
import com.rbs.pokecompose.domain.usecase.RegisterUseCase
import com.rbs.pokecompose.domain.usecase.SearchPokemonUseCase
import com.rbs.pokecompose.presentation.ui.feature.detail.DetailViewModel
import com.rbs.pokecompose.presentation.ui.feature.home.HomeViewModel
import com.rbs.pokecompose.presentation.ui.feature.login.LoginViewModel
import com.rbs.pokecompose.presentation.ui.feature.profile.ProfileViewModel
import com.rbs.pokecompose.presentation.ui.feature.register.RegisterViewModel
import com.rbs.pokecompose.presentation.ui.feature.intro.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            PokeDatabase::class.java,
            "pokemon_database"
        ).fallbackToDestructiveMigration().build()
    }
    single { get<PokeDatabase>().userDao() }
    single { get<PokeDatabase>().pokemonDao() }
    single { get<PokeDatabase>().pokemonRemoteKeysDao() }
}

val preferencesModule = module {
    single { UserPreferences(androidContext()) }
}

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApiService::class.java)
    }
}

val repositoryModule = module {
    single<PokemonRepository> { PokemonRepoImpl(get(), get()) }
    single<UserRepository> { UserRepoImpl(get(), get()) }
}

val useCaseModule = module {
    factory { GetPokemonsUseCase(get()) }
    factory { SearchPokemonUseCase(get()) }
    factory { GetDetailPokemonUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { GetStatusUseCase(get()) }
    factory { GetUserUseCase(get()) }
    factory { LogoutUseCase(get()) }
}

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { DetailViewModel(get()) }
}
