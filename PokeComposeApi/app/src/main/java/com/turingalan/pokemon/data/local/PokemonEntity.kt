package com.turingalan.pokemon.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.turingalan.pokemon.data.model.Pokemon

//"Esta clase representa una tabla en la base de datos llamada 'pokemon'".
//Cada instancia de PokemonEntity será una fila de la tabla.
@Entity("pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val sprite: String?,
    val artwork: String?,
)

//Entity → cómo se guarda en la base de datos (Room)
fun Pokemon.toEntity(): PokemonEntity {
    return PokemonEntity(
        id = this.id,
        name = this.name,
        sprite = this.sprite,
        artwork = this.artwork
    )
}

//para convertir la lista a modelo de base de datos
fun List<Pokemon>.toEntity():List<PokemonEntity> = this.map(Pokemon::toEntity)

//Model → cómo se usa en la app, más amigable para la UI
fun PokemonEntity.toModel(): Pokemon {
    return Pokemon(
        id = this.id,
        name = this.name,
        sprite = this.sprite ?: "",
        artwork = this.artwork ?: ""
    )
}
//Convierte listas completas de la DB a listas de modelos para la app.
fun List<PokemonEntity>.toModel(): List<Pokemon> {
    return this.map(PokemonEntity::toModel)
}