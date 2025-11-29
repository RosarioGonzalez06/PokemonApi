package com.turingalan.pokemon.data.remote.model

import com.google.gson.annotations.SerializedName

//Representa la respuesta de la API cuando pides la lista de Pokémon.
data class PokemonListRemote(
    val results: List<PokemonListItemRemote>
)

//Cada Pokémon en la lista
data class PokemonListItemRemote(
    val name: String,
    val url: String
)

//Modelo de un Pokémon individual
data class PokemonRemote(
    val id: Long,
    val name: String,
    val sprites: PokemonSprites,
)

//Para foto
data class PokemonSprites(
    val front_default: String,
    //otras versiones de sprites, generalmente para ilustraciones más bonitas.
    val other: PokemonOtherSprite
)

data class PokemonOtherSprite(
    //@SerializedName porque el JSON de la API tiene un guion (official-artwork), y Kotlin no permite nombres con guion.
    @SerializedName("official-artwork")
    //contiene la ilustración oficial grande del Pokémon.
    val officialArtwork: PokemonOfficialArtwork
)

data class PokemonOfficialArtwork(
    //URL de la imagen oficial grande.
    val front_default: String
)

//ejemplo
//{
//  "id": 1,
//  "name": "bulbasaur",
//  "sprites": {
//    "front_default": "url_sprite",
//    "other": {
//      "official-artwork": {
//        "front_default": "url_artwork"
//      }
//    }
//  }
//}