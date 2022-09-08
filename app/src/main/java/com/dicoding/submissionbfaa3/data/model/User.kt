package com.dicoding.submissionbfaa3.data.model

import android.content.ContentValues
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tb_favorite")
data class User (
    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String="",
    @SerializedName("html_url")
    @ColumnInfo(name = "html_url")
    val url: String="",
    @SerializedName("followers")
    @ColumnInfo(name = "followers")
    val followers: Int=0,
    @SerializedName("following")
    @ColumnInfo(name = "following")
    val following: Int=0,
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int=0,
    @SerializedName("location")
    @ColumnInfo(name = "location")
    val location: String? = "",
    @SerializedName("login")
    @ColumnInfo(name = "login")
    val login: String="",
    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String?="",
    @SerializedName("public_repos")
    @ColumnInfo(name = "public_repos")
    val publicRepos: String = "0",
) : Parcelable {
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
                url = contentValues.getAsString("html_url"),
            )
        }
    }
}