package com.turingalan.pokemon.data

import com.turingalan.pokemon.data.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    //Este método solo tiene sentido en un LocalDataSource.
    //Guarda varios Pokémon en la base de datos.
    //Lo usas cuando el repositorio hace refresh().
    suspend fun addAll(pokemonList: List<Pokemon>)

    //añadir uno
    //suspend fun addOne(pokemon: Pokemon): Result<Long>

    //Este método sirve para observar datos en tiempo real.
    //LocalDataSource → Room emite automáticamente cada vez que cambia la tabla.
    //RemoteDataSource → normalmente no tendría un flujo (la API no puede emitir eventos).
    fun observe(): Flow<Result<List<Pokemon>>>

    //Carga todos los Pokémon"
    //LocalDataSource → leer la tabla completa
    //RemoteDataSource → llamar al endpoint /pokemon
    suspend fun readAll(): Result<List<Pokemon>>

    //"Dame un Pokémon específico"
    //Remoto → /pokemon/{id}
    //Local → pokemonDao.findById(id)
    suspend fun readOne(id: Long): Result<Pokemon>
    suspend fun delete(pokemon: Pokemon): Result<Int>
    suspend fun update(pokemon: Pokemon): Result<Int>
    suspend fun isError()
}