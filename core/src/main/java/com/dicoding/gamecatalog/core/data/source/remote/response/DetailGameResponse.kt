package com.dicoding.gamecatalog.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailGameResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description_raw")
    val descriptionRaw: String?,

    @field:SerializedName("released")
    val released: String?,

    @field:SerializedName("background_image")
    val backgroundImage: String?,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("genres")
    val genres: List<GenreResponse>?
)
