package com.turingalan.pokemon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

//Esta clase representa la base de datos ROOM completa
@Database(
    //Aquí declaras qué tablas tiene tu base de datos.
    //Si hubiera más: entities = [PokemonEntity::class, TrainerEntity::class, ItemsEntity::class]
    entities = [PokemonEntity::class],
    //Es la versión de tu base de datos.
    //Si cambias columnas, estructuras o entidades, debes subirla
    version = 1)
abstract class PokemonDatabase(): RoomDatabase() {
//Este método le dice a Room:
//“Genera una implementación de PokemonDao y dame acceso a ella”.
    abstract fun getPokemonDao(): PokemonDao
}