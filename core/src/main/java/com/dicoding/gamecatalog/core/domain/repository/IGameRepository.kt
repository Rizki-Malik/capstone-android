package com.dicoding.gamecatalog.core.domain.repository

import com.dicoding.gamecatalog.core.domain.model.Game
import com.dicoding.gamecatalog.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IGameRepository {

    fun getAllGame(): Flow<Resource<List<Game>>>

    fun searchGame(query: String): Flow<Resource<List<Game>>>

    fun getDetailGame(id: Int): Flow<Resource<Game>>

    fun getFavoriteGame(): Flow<List<Game>>

    fun isFavoriteGame(id: Int): Flow<Boolean>

    suspend fun setFavoriteGame(game: Game, state: Boolean)

}
