package com.dicoding.submissionbfaa3.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import com.dicoding.submissionbfaa3.data.local.FavoriteDao
import com.dicoding.submissionbfaa3.data.local.FavoriteDatabase
import com.dicoding.submissionbfaa3.data.model.User


class MyContentProvider : ContentProvider() {
    private lateinit var dao: FavoriteDao

    companion object {
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

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (FAVORITE_ID) {
            uriMatcher.match(uri) -> dao.deleteById(ContentUris.parseId(uri))
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (FAVORITE_ID) {
            uriMatcher.match(uri) -> dao.insertFavorite(User.fromContentValue(values!!))
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun onCreate(): Boolean {
        dao = Room.databaseBuilder(
            context!!, FavoriteDatabase::class.java,
            FavoriteDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build().favoriteDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?,
    ): Cursor? {

        return when (uriMatcher.match(uri)) {
            FAVORITE -> dao.getListFavoriteProvider()
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?,
    ): Int {
        return 0
    }
}