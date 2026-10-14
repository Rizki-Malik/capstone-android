package com.dicoding.gamecatalog.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.gamecatalog.core.domain.model.Game
import com.dicoding.gamecatalog.core.domain.usecase.GameUseCase
import com.dicoding.gamecatalog.core.ui.model.GameModel
import com.dicoding.gamecatalog.core.utils.DataMapper
import com.dicoding.gamecatalog.core.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class HomeViewModel(gameUseCase: GameUseCase) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    val game = searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                gameUseCase.getAllGame()
            } else {
                gameUseCase.searchGame(query.trim())
            }
        }
        .map { mapToPresentation(it) }
        .asLiveData()

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    private fun mapToPresentation(resource: Resource<List<Game>>): Resource<List<GameModel>> =
        when (resource) {
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(
                DataMapper.mapDomainsToPresentation(resource.data.orEmpty())
            )
            is Resource.Error -> Resource.Error(resource.message.orEmpty())
        }
}
