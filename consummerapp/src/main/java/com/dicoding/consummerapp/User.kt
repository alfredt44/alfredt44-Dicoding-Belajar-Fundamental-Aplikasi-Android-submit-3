package com.dicoding.consummerapp

import android.content.ContentValues
import android.os.Parcelable


data class User (
    val avatarUrl: String="",
    val url: String="",
    val followers: Int=0,
    val following: Int=0,
    val id: Int=0,
    val location: String? = "",
    val login: String="",
    val name: String?="",
    val publicRepos: String = "0",
) {
    companion object {
        fun fromContentValue(contentValues: ContentValues): User {
            return User(
                id = contentValues.getAsInteger("id"),
                name = contentValues.getAsString("name"),
                login = contentValues.getAsString("login"),
                location = contentValues.getAsString("location"),
                following = contentValues.getAsInteger("following"),
                followers = contentValues.getAsInteger("followers"),
                avatarUrl = contentValues.getAsString("avatar_url"),
                publicRepos = contentValues.getAsString("public_repos"),
            )
        }
    }
}