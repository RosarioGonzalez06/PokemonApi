package com.turingalan.pokemon.data.remote

import com.turingalan.pokemon.data.remote.model.PokemonListRemote
import com.turingalan.pokemon.data.remote.model.PokemonRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//la pieza que convierte tus funciones en llamadas HTTP reales a la PokéAPI.
//Es una interfaz que Retrofit usa para generar automáticamente el código que hace las peticiones HTTP.
interface PokemonApi {
    @GET("/api/v2/pokemon")
    suspend fun readAll(
        @Query("limit")limit: Int = 20,
        @Query("offset")offset: Int = 0
        //una respuesta HTTP que contiene un objeto con la lista
    ): Response<PokemonListRemote>

    //devuelve pokemon individual segun id o nombre
    @GET("/api/v2/pokemon/{id}")
    suspend fun readOne(@Path("id") id: Long): Response<PokemonRemote>
    @GET("/api/v2/pokemon/{name}")
    suspend fun readOne(@Path("name") name: String): Response<PokemonRemote>
}