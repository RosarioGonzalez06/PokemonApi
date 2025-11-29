package com.turingalan.pokemon.di

import com.turingalan.pokemon.data.remote.PokemonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
//El RemoteModule sirve para que Hilt pueda crear:
//El Retrofit + PokemonApi, que se usa para hacer peticiones HTTP.
//Un CoroutineScope compartido, para lanzar trabajos en segundo plano.
@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {
    @Provides
    @Singleton
    //Crea una única instancia Retrofit en toda la app
    //(🥇 Súper importante, porque Retrofit se debe instanciar una vez).
    fun providePokemonApi(): PokemonApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(PokemonApi::class.java)
    }

    //“Cuando necesites un CoroutineScope para tareas de fondo, usa este”.
    //Lo utiliza por ejemplo PokemonLocalDataSource o PokemonRemoteDataSource.
    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        //Supervisor:evita que si una corutina falla, cancelen todas las demás.
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
}