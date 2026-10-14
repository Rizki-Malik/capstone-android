package com.dicoding.gamecatalog.core.data

import com.dicoding.gamecatalog.core.data.source.local.LocalDataSource
import com.dicoding.gamecatalog.core.data.source.remote.RemoteDataSource
import com.dicoding.gamecatalog.core.data.source.remote.network.ApiResponse
import com.dicoding.gamecatalog.core.data.source.remote.response.GameResponse
import com.dicoding.gamecatalog.core.domain.model.Game
import com.dicoding.gamecatalog.core.domain.repository.IGameRepository
import com.dicoding.gamecatalog.core.utils.DataMapper
import com.dicoding.gamecatalog.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GameRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IGameRepository {

    override fun getAllGame(): Flow<Resource<List<Game>>> =
        object : NetworkBoundResource<List<Game>, List<GameResponse>>() {
            override fun loadFromDB(): Flow<List<Game>> {
                return localDataSource.getAllGame().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Game>?): Boolean =
                data.isNullOrEmpty() // ambil data dari internet hanya jika database masih kosong

            override suspend fun createCall(): Flow<ApiResponse<List<GameResponse>>> =
                remoteDataSource.getAllGame()

            override suspend fun saveCallResult(data: List<GameResponse>) {
                val gameList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGame(gameList)
            }
        }.asFlow()

    override fun searchGame(query: String): Flow<Resource<List<Game>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.searchGame(query).first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(DataMapper.mapResponsesToDomain(apiResponse.data)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success(emptyList()))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getDetailGame(id: Int): Flow<Resource<Game>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.getDetailGame(id).first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(DataMapper.mapDetailResponseToDomain(apiResponse.data)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error("Data not found"))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getFavoriteGame(): Flow<List<Game>> {
        return localDataSource.getFavoriteGame().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun isFavoriteGame(id: Int): Flow<Boolean> =
        localDataSource.isFavoriteGame(id)

    override suspend fun setFavoriteGame(game: Game, state: Boolean) {
        val gameEntity = DataMapper.mapDomainToEntity(game)
        localDataSource.setFavoriteGame(gameEntity, state)
    }
}
