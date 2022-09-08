package com.dicoding.consummerapp

import android.content.UriMatcher
import android.net.Uri

object ProviderUtil {
    private const val FAVORITE = 1
    private const val FAVORITE_ID = 2
    private const val SCHEME = "content"
    private const val AUTHORITY = "com.dicoding.submissionbfaa3"
    private const val TB_NAME = "tb_favorite"
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TB_NAME)
        .build()
    init {
        uriMatcher.addURI(AUTHORITY, TB_NAME, FAVORITE)
        uriMatcher.addURI(AUTHORITY, "$TB_NAME/#", FAVORITE_ID)
    }
}