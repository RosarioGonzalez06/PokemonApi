package com.turingalan.pokemon.data.remote

import com.turingalan.pokemon.data.PokemonDataSource
import com.turingalan.pokemon.data.model.Pokemon
import com.turingalan.pokemon.data.remote.model.PokemonRemote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

//Es una clase que:
//✔ Llama a Retrofit → obtiene datos
//✔ Transforma los datos remotos → en objetos Pokemon
//✔ Devuelve Resultados → Result<List<Pokemon>> o Result<Pokemon>
//✔ Emite flujos (Flow) → si la UI quiere observar cambios
//✔ Implementa PokemonDataSource → Respeta un contrato común
//(Luego podrás cambiar remoto/local sin romper nada.)
//Es como un reportero que va a la PokéAPI, trae noticias, las resume y te las entrega.

class PokemonRemoteDataSource @Inject constructor(
    private val api: PokemonApi,
    private val scope: CoroutineScope
): PokemonDataSource {
    //en remoto no se puede añadir nada a API
    override suspend fun addAll(pokemonList: List<Pokemon>) {
        TODO("Not yet implemented")
    }

    override fun observe(): Flow<Result<List<Pokemon>>> {
        return flow {
            //Emite una lista vacía:La UI puede mostrar "Cargando…".
            emit(Result.success(listOf<Pokemon>()))
            //Llama a readAll() → obtiene los Pokémon de la API.
            val result = readAll()
            //Emite el resultado real.
            emit(result)
            //reutiliza los resultados para varios observers.
        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000L),
            replay = 1
        )
    }

//    override fun observe(): Flow<Result<List<Pokemon>>> {
//        return flow {
//            emit(Result.success(listOf<Pokemon>()))
//            val finalList = mutableListOf<Pokemon>()
//            val limit = 20
//            val offset = 20
//            var thereAreMoreResults = false
//            do {
//                try {
//                    val response = api.readAll(limit, offset)
//                    if (response.isSuccessful) {
//                        val body =
//                    }
//                }
//                catch ()
//            } while (!thereAreMoreResults)
//        }
//    }

    override suspend fun readAll(): Result<List<Pokemon>> {
        //TODO REMOVE CODIGO MALO
        try {
            //Pide la primera página de Pokémon
            val response = api.readAll(limit = 40, offset = 0)
            //La API devuelve nombres + URLs
            val finalList = mutableListOf<Pokemon>()
            return if (response.isSuccessful) {
                val body = response.body()!!
                //Por cada nombre → pides los datos completos
                //Convertimos ese JSON complejo (sprites, artwork, etc.) en tu modelo Pokemon
                for (result in body.results) {
                    val remotePokemon = readOne(name = result.name)
                    remotePokemon?.let {
                        finalList.add(it)
                    }
                }
                Result.success(finalList)
            } else {
                val status = response.code() //tipo de error
                Result.failure(RuntimeException())
            }
        }
        catch (ex: Exception) {
            return Result.failure(ex)
        }
    }

    //Se usa internamente durante readAll().
    private suspend fun readOne(name: String): Pokemon? {
        val response = api.readOne(name)
        return if (response.isSuccessful) {
            response.body()!!.toExternal()
        }
        else {
            null
        }
    }
//Esto se usa cuando la UI pide un Pokémon concreto.
    override suspend fun readOne(id: Long): Result<Pokemon> {
        val response = api.readOne(id)
        return if (response.isSuccessful) {
            val pokemon = response.body()!!.toExternal()
            Result.success(pokemon)
        }
        else {
            Result.failure(RuntimeException())
        }
    }

    override suspend fun delete(pokemon: Pokemon):Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun update(pokemon: Pokemon):Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }
}

//Por qué existe esto?
//Porque la API tiene un JSON así:
//sprites.other.official-artwork.front_default
//pero tú quieres un modelo simple:
//sprite = "...",
//artwork = "..."
//Esto limpia los datos y los adapta a tu app.

//solo para apis complejas que tienen muchos subapartados
fun PokemonRemote.toExternal(): Pokemon {
    return Pokemon(
        id = this.id,
        name = this.name,
        sprite = this.sprites.front_default,
        artwork = this.sprites.other.officialArtwork.front_default
    )
}