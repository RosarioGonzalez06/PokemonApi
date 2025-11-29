package com.turingalan.pokemon.data.local

//Es una clase que extiende RuntimeException
//PokemonNotFoundException literalmente dice: “el Pokémon que buscabas no se encontró”.
//Es más intuitivo que lanzar una RuntimeException genérica.
class PokemonNotFoundException: RuntimeException() {

}