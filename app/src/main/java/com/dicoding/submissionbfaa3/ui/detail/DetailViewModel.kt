package com.dicoding.submissionbfaa3.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionbfaa3.data.local.FavoriteDao
import com.dicoding.submissionbfaa3.data.local.FavoriteDatabase
import com.dicoding.submissionbfaa3.data.model.User
import com.dicoding.submissionbfaa3.data.remote.ApiClient
import com.dicoding.submissionbfaa3.data.remote.ApiService
import com.dicoding.submissionbfaa3.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : ViewModel() {
    private val dao: FavoriteDao by lazy { FavoriteDatabase.getDatabase(application).favoriteDao() }
    var username: String? = null
    private val api: ApiService by lazy { ApiClient.getApiClient() }
    fun getDetail(username: String?) = liveData<Resource<User>> {
        try {
            val response = api.searchDetail(username)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()))
            } else {
                emit(Resource.Failure(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Failure(e.message))

        }
    }

    fun getListFollowers() = liveData<Resource<List<User>>> {
        try {
            val response = api.listFolowers(username)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()))
            } else {
                emit(Resource.Failure(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Failure(e.message))

        }
    }

    fun getListFollowing() = liveData<Resource<List<User>>> {
        try {
            val response = api.listFollowing(username)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()))
            } else {
                emit(Resource.Failure(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Failure(e.message))

        }
    }

    fun isFavorite(id: Int) = liveData(Dispatchers.IO) {
        emitSource(dao.checkFavorite(id))
    }

    fun addToFavorite(data: User) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertFavorite(data)
        }
    }

    fun deleteFavorite(data: User) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteFavorite(data)
        }
    }
}