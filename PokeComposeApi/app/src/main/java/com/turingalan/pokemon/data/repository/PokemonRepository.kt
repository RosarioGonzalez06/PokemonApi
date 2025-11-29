package com.turingalan.pokemon.data.repository

import com.turingalan.pokemon.data.model.Pokemon
import kotlinx.coroutines.flow.Flow

//Es una interfaz que define cómo tu aplicación pide Pokémon, sin importar de dónde vienen.
interface PokemonRepository {

    suspend fun readOne(id:Long): Result<Pokemon>
    suspend fun readAll(): Result<List<Pokemon>>
    //Devuelve un flujo (Flow) que se actualiza automáticamente.
    //Esto se usa típicamente con Room:
    //Cambia la base de datos →
    //Room emite un nuevo valor →
    //La UI se actualiza automáticamente (sin tocar nada)
    fun observe(): Flow<Result<List<Pokemon>>>
}