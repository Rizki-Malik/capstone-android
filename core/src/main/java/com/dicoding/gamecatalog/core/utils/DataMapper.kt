package com.dicoding.gamecatalog.core.utils

import com.dicoding.gamecatalog.core.data.source.local.entity.GameEntity
import com.dicoding.gamecatalog.core.data.source.remote.response.DetailGameResponse
import com.dicoding.gamecatalog.core.data.source.remote.response.GameResponse
import com.dicoding.gamecatalog.core.domain.model.Game
import com.dicoding.gamecatalog.core.ui.model.GameModel

object DataMapper {

    fun mapResponsesToEntities(input: List<GameResponse>): List<GameEntity> {
        val gameList = ArrayList<GameEntity>()
        input.map {
            val game = GameEntity(
                gameId = it.id,
                name = it.name,
                released = it.released.orEmpty(),
                rating = it.rating,
                backgroundImage = it.backgroundImage.orEmpty(),
                genres = it.genres?.joinToString(", ") { genre -> genre.name }.orEmpty(),
                description = "",
                isFavorite = false,
                isHome = true
            )
            gameList.add(game)
        }
        return gameList
    }

    fun mapEntitiesToDomain(input: List<GameEntity>): List<Game> =
        input.map {
            Game(
                id = it.gameId,
                name = it.name,
                released = it.released,
                rating = it.rating,
                backgroundImage = it.backgroundImage,
                genres = it.genres,
                description = it.description,
                isFavorite = it.isFavorite
            )
        }

    fun mapResponsesToDomain(input: List<GameResponse>): List<Game> =
        input.map {
            Game(
                id = it.id,
                name = it.name,
                released = it.released.orEmpty(),
                rating = it.rating,
                backgroundImage = it.backgroundImage.orEmpty(),
                genres = it.genres?.joinToString(", ") { genre -> genre.name }.orEmpty(),
                description = "",
                isFavorite = false
            )
        }

    fun mapDetailResponseToDomain(input: DetailGameResponse) = Game(
        id = input.id,
        name = input.name,
        released = input.released.orEmpty(),
        rating = input.rating,
        backgroundImage = input.backgroundImage.orEmpty(),
        genres = input.genres?.joinToString(", ") { genre -> genre.name }.orEmpty(),
        description = input.descriptionRaw.orEmpty(),
        isFavorite = false
    )

    fun mapDomainToEntity(input: Game) = GameEntity(
        gameId = input.id,
        name = input.name,
        released = input.released,
        rating = input.rating,
        backgroundImage = input.backgroundImage,
        genres = input.genres,
        description = input.description,
        isFavorite = input.isFavorite
    )

    fun mapDomainsToPresentation(input: List<Game>): List<GameModel> =
        input.map { mapDomainToPresentation(it) }

    fun mapDomainToPresentation(input: Game) = GameModel(
        id = input.id,
        name = input.name,
        released = input.released,
        rating = input.rating,
        backgroundImage = input.backgroundImage,
        genres = input.genres,
        description = input.description,
        isFavorite = input.isFavorite
    )

    fun mapPresentationToDomain(input: GameModel) = Game(
        id = input.id,
        name = input.name,
        released = input.released,
        rating = input.rating,
        backgroundImage = input.backgroundImage,
        genres = input.genres,
        description = input.description,
        isFavorite = input.isFavorite
    )
}
