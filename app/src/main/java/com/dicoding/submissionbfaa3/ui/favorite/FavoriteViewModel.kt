package com.dicoding.submissionbfaa3.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.submissionbfaa3.data.local.FavoriteDao
import com.dicoding.submissionbfaa3.data.local.FavoriteDatabase
import com.dicoding.submissionbfaa3.data.model.User
import com.dicoding.submissionbfaa3.util.Resource
import kotlinx.coroutines.Dispatchers
import okio.IOException


class FavoriteViewModel (application : Application): ViewModel() {
    private val dao : FavoriteDao by lazy { FavoriteDatabase.getDatabase(application).favoriteDao() }
    fun getListFavorite() = liveData<Resource<List<User>>>(Dispatchers.IO) {
        try {
            val data = dao.getListFavorite()
            emit(Resource.Success(data))
        }catch (e: IOException){
            emit(Resource.Failure(e.message))
        }
    }

}
