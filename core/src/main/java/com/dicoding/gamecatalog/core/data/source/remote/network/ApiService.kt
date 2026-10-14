package com.dicoding.gamecatalog.core.data.source.remote.network

import com.dicoding.gamecatalog.core.data.source.remote.response.DetailGameResponse
import com.dicoding.gamecatalog.core.data.source.remote.response.ListGameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getListGame(
        @Query("page_size") pageSize: Int = 20
    ): ListGameResponse

    @GET("games")
    suspend fun searchGame(
        @Query("search") query: String,
        @Query("page_size") pageSize: Int = 20
    ): ListGameResponse

    @GET("games/{id}")
    suspend fun getDetailGame(
        @Path("id") id: Int
    ): DetailGameResponse
}
