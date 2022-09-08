package com.dicoding.consummerapp

import android.database.Cursor


object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor?): List<User> {
        val list = arrayListOf<User>()
        cursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val name = getString(getColumnIndexOrThrow("name"))
                val login = getString(getColumnIndexOrThrow("login"))
                val location = getString(getColumnIndexOrThrow("location"))
                val following = getInt(getColumnIndexOrThrow("following"))
                val followers = getInt(getColumnIndexOrThrow("followers"))
                val avatarUrl = getString(getColumnIndexOrThrow("avatar_url"))
                val url = getString(getColumnIndexOrThrow("html_url"))
                val publicRepos = getString(getColumnIndexOrThrow("public_repos"))
                list.add(
                    User(
                    id = id,
                    name = name,
                    login = login,
                    location = location,
                    following = following,
                    followers = followers,
                    avatarUrl = avatarUrl,
                    url = url,
                    publicRepos = publicRepos
                )
                )
            }

        }
        return list
    }
}