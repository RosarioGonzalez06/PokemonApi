package com.turingalan.pokemon.data.local

import com.turingalan.pokemon.data.PokemonDataSource
import com.turingalan.pokemon.data.model.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonLocalDataSource @Inject constructor(
    private val scope: CoroutineScope,
    private val pokemonDao: PokemonDao
): PokemonDataSource {

    //añadir pokemons a la BD
    override suspend fun addAll(pokemonList: List<Pokemon>) {
        val mutex = Mutex()
        pokemonList.forEach { pokemon ->
            //Convierte tu Pokémon de modelo de app a entidad de DB
            withContext(Dispatchers.IO) {
                pokemonDao.insert(pokemon.toEntity())
            }
        }
    }
    //añadir uno
    //override suspend fun addOne(pokemon: Pokemon): Result<Long> {
    //    val id = pokemonDao.insert(pokemon.toEntity())
    //
    //    return if (id != -1L) {
    //        Result.success(id)
    //    } else {
    //        Result.failure(Exception("Error inserting pokemon"))
    //    }
    //}

    //Si la lista de Pokémon cambia, esta función lo notificará automáticamente.
    override fun observe(): Flow<Result<List<Pokemon>>> {
        //Te da un Flow que observa los cambios en la DB.
        val databaseFlow = pokemonDao.observeAll()
        return databaseFlow.map { entities ->
            //Convierte las entidades de la DB a modelos que tu app pueda usar
            Result.success(entities.toModel())
        }
    }

    //Esto simplemente lee todos los Pokémon de la base de datos y los convierte en modelo
    override suspend fun readAll(): Result<List<Pokemon>> {
        val result = Result.success(pokemonDao.getAll().toModel())
        return result
    }

    //Busca un Pokémon específico por su id
    override suspend fun readOne(id: Long): Result<Pokemon> {
        val entity = pokemonDao.readPokemonById(id)
        return if (entity == null)
            Result.failure(PokemonNotFoundException())
        else
            Result.success(entity.toModel())
    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }

    override suspend fun delete(pokemon: Pokemon): Result<Int> {
        val pokemonEntity = pokemon.toEntity()
        val rowsDeleted = pokemonDao.delete(pokemonEntity)

        return if (rowsDeleted > 0) {
            Result.success(rowsDeleted)
        } else {
            Result.failure(PokemonNotFoundException())
        }
    }

    override suspend fun update(pokemon: Pokemon): Result<Int> {
        val pokemonEntity = pokemon.toEntity()
        val rowsUpdated = pokemonDao.update(pokemonEntity)

        return if (rowsUpdated > 0) {
            Result.success(rowsUpdated)
        } else {
            Result.failure(PokemonNotFoundException())
        }
    }
    //eliminar todos
    //override suspend fun deleteAll(): Result<Int> {
    //        val pokemonesDeleted = pokemonDao.deleteAll()
    //        return if(pokemonesDeleted > 0){
    //            Result.success(pokemonesDeleted)
    //
    //        }else{
    //            Result.failure(PokemonNotFoundException())
    //        }
    //    }
}
