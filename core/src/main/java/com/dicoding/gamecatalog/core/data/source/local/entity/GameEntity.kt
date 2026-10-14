package com.dicoding.gamecatalog.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameEntity(
    @PrimaryKey
    @ColumnInfo(name = "gameId")
    val gameId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "released")
    val released: String,

    @ColumnInfo(name = "rating")
    val rating: Double,

    @ColumnInfo(name = "backgroundImage")
    val backgroundImage: String,

    @ColumnInfo(name = "genres")
    val genres: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false,

    @ColumnInfo(name = "isHome")
    val isHome: Boolean = false
)
