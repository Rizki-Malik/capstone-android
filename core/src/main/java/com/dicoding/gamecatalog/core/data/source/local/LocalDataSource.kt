package com.dicoding.gamecatalog.core.data.source.local

import com.dicoding.gamecatalog.core.data.source.local.entity.GameEntity
import com.dicoding.gamecatalog.core.data.source.local.room.GameDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val gameDao: GameDao) {

    fun getAllGame(): Flow<List<GameEntity>> = gameDao.getAllGame()

    fun getFavoriteGame(): Flow<List<GameEntity>> = gameDao.getFavoriteGame()

    fun isFavoriteGame(id: Int): Flow<Boolean> = gameDao.isFavoriteGame(id)

    suspend fun insertGame(gameList: List<GameEntity>) = gameDao.insertGame(gameList)

    suspend fun setFavoriteGame(game: GameEntity, newState: Boolean) {
        // game dari hasil search belum ada di database, jadi disimpan dulu dengan isHome = false
        gameDao.insertGame(listOf(game))
        gameDao.updateFavoriteGame(game.gameId, newState)
    }
}
