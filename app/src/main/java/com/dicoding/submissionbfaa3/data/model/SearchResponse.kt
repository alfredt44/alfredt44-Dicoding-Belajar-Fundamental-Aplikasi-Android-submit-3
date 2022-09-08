package com.dicoding.submissionbfaa3.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items")
    val items: List<User> = emptyList(),
    @SerializedName("total_count")
    val totalCount: Int = 0,
)