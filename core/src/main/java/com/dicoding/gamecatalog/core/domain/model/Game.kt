package com.dicoding.gamecatalog.core.domain.model

data class Game(
    val id: Int,
    val name: String,
    val released: String,
    val rating: Double,
    val backgroundImage: String,
    val genres: String,
    val description: String,
    val isFavorite: Boolean
)
