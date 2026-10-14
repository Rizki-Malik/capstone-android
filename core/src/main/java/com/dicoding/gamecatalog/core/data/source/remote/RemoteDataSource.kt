package com.dicoding.gamecatalog.core.data.source.remote

import android.util.Log
import com.dicoding.gamecatalog.core.data.source.remote.network.ApiResponse
import com.dicoding.gamecatalog.core.data.source.remote.network.ApiService
import com.dicoding.gamecatalog.core.data.source.remote.response.DetailGameResponse
import com.dicoding.gamecatalog.core.data.source.remote.response.GameResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllGame(): Flow<ApiResponse<List<GameResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getListGame()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchGame(query: String): Flow<ApiResponse<List<GameResponse>>> {
        return flow {
            try {
                val response = apiService.searchGame(query)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailGame(id: Int): Flow<ApiResponse<DetailGameResponse>> {
        return flow {
            try {
                val response = apiService.getDetailGame(id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}
