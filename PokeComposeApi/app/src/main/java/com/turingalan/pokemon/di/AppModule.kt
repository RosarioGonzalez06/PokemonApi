package com.turingalan.pokemon.di

import com.turingalan.pokemon.data.PokemonDataSource
import com.turingalan.pokemon.data.local.PokemonLocalDataSource
import com.turingalan.pokemon.data.remote.PokemonRemoteDataSource
import com.turingalan.pokemon.data.repository.PokemonRepository
import com.turingalan.pokemon.data.repository.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Singleton
    @Binds
    @RemoteDataSource
    abstract fun bindsRemotePokemonRemoteDataSource(ds: PokemonRemoteDataSource): PokemonDataSource

    @Singleton
    @Binds
    @LocalDataSource
    abstract fun bindsLocalPokemonRemoteDataSource(ds: PokemonLocalDataSource): PokemonDataSource

    @Binds
    @Singleton
    abstract fun bindPokemonRepository(repository: PokemonRepositoryImpl): PokemonRepository
    //abstract fun bindPokemonRepository(repository: PokemonFakeRemoteRepository): PokemonRepository
    //abstract fun bindPokemonRepository(repository: PokemonInMemoryRepository): PokemonRepository
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource