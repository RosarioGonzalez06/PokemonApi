package com.turingalan.pokemon.di

import android.content.Context
import androidx.room.Room
import com.turingalan.pokemon.data.local.PokemonDao
import com.turingalan.pokemon.data.local.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
//Este módulo de Hilt sirve para crear e inyectar:
//La base de datos Room (PokemonDatabase)
//El DAO (PokemonDao)
//Como tú no quieres crear estos objetos manualmente cada vez, Hilt los crea una sola vez y los comparte donde haga falta.
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton

    //Le dice a Hilt:
    //“Cuando necesites un PokemonDatabase, créalo así”.
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): PokemonDatabase {
        val database = Room.databaseBuilder(
            context = applicationContext,
            klass = PokemonDatabase::class.java,
            name = "pokemon-db"
        ).build()
        return database;
    }

    //“Si alguien necesita un PokemonDao, no lo construyas tú.
    //Pídeselo a la base de datos ya creada.”
    @Provides
    fun providePokemonDao(
        database: PokemonDatabase
    ): PokemonDao {
        return database.getPokemonDao()
    }
}