package com.dicoding.submissionbfaa3.data.remote

import com.dicoding.submissionbfaa3.data.model.SearchResponse
import com.dicoding.submissionbfaa3.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String?,
    ): Response<SearchResponse>

    @GET("users/{username}")
    suspend fun searchDetail(
        @Path("username") username: String?,
    ): Response<User>

    @GET("users/{username}/followers")
    suspend fun listFolowers(
        @Path("username") username: String?,
    ): Response<List<User>>

    @GET("users/{username}/following")
    suspend fun listFollowing(
        @Path("username") username: String?,
    ): Response<List<User>>
}