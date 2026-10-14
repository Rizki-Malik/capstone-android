package com.dicoding.gamecatalog.core.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameModel(
    val id: Int,
    val name: String,
    val released: String,
    val rating: Double,
    val backgroundImage: String,
    val genres: String,
    val description: String,
    val isFavorite: Boolean
) : Parcelable
