package com.dicoding.gamecatalog.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.gamecatalog.core.domain.usecase.GameUseCase
import com.dicoding.gamecatalog.core.ui.model.GameModel
import com.dicoding.gamecatalog.core.utils.DataMapper
import com.dicoding.gamecatalog.core.vo.Resource
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetailGameViewModel(private val gameUseCase: GameUseCase) : ViewModel() {

    fun getDetailGame(id: Int) = gameUseCase.getDetailGame(id).map { resource ->
        when (resource) {
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> resource.data?.let {
                Resource.Success(DataMapper.mapDomainToPresentation(it))
            } ?: Resource.Error("Data not found")
            is Resource.Error -> Resource.Error<GameModel>(resource.message.orEmpty())
        }
    }.asLiveData()

    fun isFavoriteGame(id: Int) = gameUseCase.isFavoriteGame(id).asLiveData()

    fun setFavoriteGame(game: GameModel, newStatus: Boolean) {
        viewModelScope.launch {
            gameUseCase.setFavoriteGame(DataMapper.mapPresentationToDomain(game), newStatus)
        }
    }
}
