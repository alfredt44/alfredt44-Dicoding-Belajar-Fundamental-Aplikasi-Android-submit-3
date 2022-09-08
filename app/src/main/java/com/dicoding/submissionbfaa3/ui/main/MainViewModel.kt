package com.dicoding.submissionbfaa3.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.submissionbfaa3.data.model.User
import com.dicoding.submissionbfaa3.data.remote.ApiClient
import com.dicoding.submissionbfaa3.data.remote.ApiService
import com.dicoding.submissionbfaa3.util.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel : ViewModel() {
    private val api : ApiService by lazy { ApiClient.getApiClient() }
    fun searchUser(query : String?) = liveData<Resource<List<User>>>(Dispatchers.IO){
        try {
            val response = api.searchUser(query)
            if (response.isSuccessful){
                emit(Resource.Success(response.body()!!.items))
            }else{
                emit(Resource.Failure(response.message()))
            }
        }catch (e: Exception){
            emit(Resource.Failure(e.message))
        }
    }
}