package com.dicoding.gamecatalog.core.utils

import com.dicoding.gamecatalog.core.data.source.remote.response.DetailGameResponse
import com.dicoding.gamecatalog.core.data.source.remote.response.GameResponse
import com.dicoding.gamecatalog.core.data.source.remote.response.GenreResponse
import com.dicoding.gamecatalog.core.domain.model.Game
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DataMapperTest {

    private val dummyResponse = GameResponse(
        id = 3498,
        name = "Grand Theft Auto V",
        released = "2013-09-17",
        backgroundImage = "https://media.rawg.io/media/games/gta5.jpg",
        rating = 4.47,
        genres = listOf(GenreResponse(4, "Action"), GenreResponse(3, "Adventure"))
    )

    @Test
    fun mapResponsesToEntities_shouldJoinGenresAndMarkAsHome() {
        val entities = DataMapper.mapResponsesToEntities(listOf(dummyResponse))

        assertEquals(1, entities.size)
        assertEquals(dummyResponse.id, entities[0].gameId)
        assertEquals("Action, Adventure", entities[0].genres)
        assertFalse(entities[0].isFavorite)
        assertTrue(entities[0].isHome)
    }

    @Test
    fun mapDomainToEntity_shouldNotMarkAsHome() {
        val game = DataMapper.mapResponsesToDomain(listOf(dummyResponse))[0]

        val entity = DataMapper.mapDomainToEntity(game)

        assertFalse(entity.isHome)
    }

    @Test
    fun mapDomainsToPresentation_shouldKeepOrderAndSize() {
        val second = dummyResponse.copy(id = 3328, name = "The Witcher 3: Wild Hunt")
        val games = DataMapper.mapResponsesToDomain(listOf(dummyResponse, second))

        val result = DataMapper.mapDomainsToPresentation(games)

        assertEquals(2, result.size)
        assertEquals(3498, result[0].id)
        assertEquals(3328, result[1].id)
    }

    @Test
    fun mapResponsesToDomain_whenFieldIsNull_shouldReturnEmptyString() {
        val response = dummyResponse.copy(released = null, backgroundImage = null, genres = null)

        val game = DataMapper.mapResponsesToDomain(listOf(response))[0]

        assertEquals("", game.released)
        assertEquals("", game.backgroundImage)
        assertEquals("", game.genres)
    }

    @Test
    fun mapDetailResponseToDomain_shouldUseDescriptionRaw() {
        val response = DetailGameResponse(
            id = 3498,
            name = "Grand Theft Auto V",
            descriptionRaw = "Rockstar Games went bigger.",
            released = "2013-09-17",
            backgroundImage = "https://media.rawg.io/media/games/gta5.jpg",
            rating = 4.47,
            genres = listOf(GenreResponse(4, "Action"))
        )

        val game = DataMapper.mapDetailResponseToDomain(response)

        assertEquals("Rockstar Games went bigger.", game.description)
        assertEquals("Action", game.genres)
    }

    @Test
    fun mapDomainToPresentation_andBack_shouldReturnSameGame() {
        val game = Game(
            id = 3498,
            name = "Grand Theft Auto V",
            released = "2013-09-17",
            rating = 4.47,
            backgroundImage = "https://media.rawg.io/media/games/gta5.jpg",
            genres = "Action",
            description = "",
            isFavorite = true
        )

        val result = DataMapper.mapPresentationToDomain(DataMapper.mapDomainToPresentation(game))

        assertEquals(game, result)
    }
}
