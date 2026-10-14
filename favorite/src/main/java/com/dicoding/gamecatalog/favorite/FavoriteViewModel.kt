package com.dicoding.gamecatalog.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.gamecatalog.core.domain.usecase.GameUseCase
import com.dicoding.gamecatalog.core.utils.DataMapper
import kotlinx.coroutines.flow.map

class FavoriteViewModel(gameUseCase: GameUseCase) : ViewModel() {
    val favoriteGame = gameUseCase.getFavoriteGame()
        .map { DataMapper.mapDomainsToPresentation(it) }
        .asLiveData()
}
