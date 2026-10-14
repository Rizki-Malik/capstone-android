package com.dicoding.gamecatalog.core.domain.usecase

import com.dicoding.gamecatalog.core.domain.model.Game
import com.dicoding.gamecatalog.core.domain.repository.IGameRepository

class GameInteractor(private val gameRepository: IGameRepository) : GameUseCase {

    override fun getAllGame() = gameRepository.getAllGame()

    override fun searchGame(query: String) = gameRepository.searchGame(query)

    override fun getDetailGame(id: Int) = gameRepository.getDetailGame(id)

    override fun getFavoriteGame() = gameRepository.getFavoriteGame()

    override fun isFavoriteGame(id: Int) = gameRepository.isFavoriteGame(id)

    override suspend fun setFavoriteGame(game: Game, state: Boolean) =
        gameRepository.setFavoriteGame(game, state)
}
