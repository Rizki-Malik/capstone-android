package com.dicoding.gamecatalog.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.gamecatalog.core.data.source.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query("SELECT * FROM game WHERE isHome = 1")
    fun getAllGame(): Flow<List<GameEntity>>

    @Query("SELECT * FROM game WHERE isFavorite = 1")
    fun getFavoriteGame(): Flow<List<GameEntity>>

    @Query("SELECT EXISTS(SELECT * FROM game WHERE gameId = :id AND isFavorite = 1)")
    fun isFavoriteGame(id: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGame(game: List<GameEntity>)

    @Query("UPDATE game SET isFavorite = :state WHERE gameId = :id")
    suspend fun updateFavoriteGame(id: Int, state: Boolean)
}
