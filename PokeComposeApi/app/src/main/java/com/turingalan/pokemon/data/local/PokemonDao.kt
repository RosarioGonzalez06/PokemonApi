package com.turingalan.pokemon.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//La app NO habla directamente con la base de datos. Le pide al DAO.
//El DAO se encarga del trabajo sucio: crear, leer, actualizar y borrar datos.
@Dao
interface PokemonDao {

    //Si ya existe un Pokémon con el mismo ID, se sustituye.(onConflict)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonEntity):Long

    //insertar lista de pokemon
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemonList: List<PokemonEntity>)

    @Update
    suspend fun update(pokemon: PokemonEntity):Int

    //eliminar uno
    @Delete
    suspend fun delete(pokemon: PokemonEntity): Int

    //eliminar todos
    //@Query("DELETE FROM pokemon")
    //    suspend fun deleteAll(): Int

    @Query("SELECT * FROM pokemon")
    suspend fun getAll(): List<PokemonEntity>

    //Devuelve un Flow, que emite actualizaciones automáticas.
    @Query("SELECT * FROM pokemon")
    fun observeAll(): Flow<List<PokemonEntity>>
    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun readPokemonById(id: Long): PokemonEntity?
}