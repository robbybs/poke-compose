package com.rbs.pokecompose

import android.app.Application
import com.rbs.pokecompose.di.databaseModule
import com.rbs.pokecompose.di.networkModule
import com.rbs.pokecompose.di.preferencesModule
import com.rbs.pokecompose.di.repositoryModule
import com.rbs.pokecompose.di.useCaseModule
import com.rbs.pokecompose.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PokeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PokeApp)
            printLogger(Level.DEBUG)
            modules(
                databaseModule,
                preferencesModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}