package com.turingalan.pokemon.data.repository

import com.turingalan.pokemon.data.model.Pokemon
import com.turingalan.pokemon.data.PokemonDataSource
import com.turingalan.pokemon.di.LocalDataSource
import com.turingalan.pokemon.di.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

//Es el cerebro que decide entre datos remotos y locales.
class PokemonRepositoryImpl @Inject constructor(
    @RemoteDataSource private val remoteDataSource: PokemonDataSource,
    @LocalDataSource private val localDataSource: PokemonDataSource,
    private val scope: CoroutineScope
): PokemonRepository {
    //Cuando quiero un Pokémon individual, voy siempre al remoto
    override suspend fun readOne(id: Long): Result<Pokemon> {
        return remoteDataSource.readOne(id)
    }
    //Leer todos los Pokémon una vez → desde la API
    override suspend fun readAll(): Result<List<Pokemon>> {
        return remoteDataSource.readAll()
    }

    //La pantalla recibe actualizaciones AUTOMÁTICAS sin usar LiveData 🔥
    //Funciona incluso sin Internet (porque lee de local) 🔥
    //Es reactivo, moderno y profesional
    override fun observe(): Flow<Result<List<Pokemon>>> {
        //Empieza a refrescar los datos (en segundo plano)
        //Llama a la API
        //→ Descarga todos los Pokémon
        //→ Los guarda en local
        scope.launch {
            refresh()
        }
        //Devuelve un Flow que viene desde LOCAL
        //Room emite automáticamente
        //Una lista vacía
        //La lista actual en BD
        //La nueva lista cuando refresh() acabe
        return localDataSource.observe()
    }

    //Esto es una sincronización remota → local.
    //Llama a la API para bajar la lista completa
    //Transformas los resultados remotos a modelos internos
    //Guardas esos Pokémon en la BD local
    //Room emite un nuevo valor → la UI se actualiza sola
    //Como una Pokédex que descarga datos y los guarda.
    private suspend fun refresh() {
        val resultRemotePokemon = remoteDataSource.readAll()
        if (resultRemotePokemon.isSuccess) {
            localDataSource.addAll(resultRemotePokemon.getOrNull()!!)
        }
//        else {
//            localDataSource.isError()
//        }
    }
}