package com.dicoding.submissionbfaa3.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.submissionbfaa3.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao():FavoriteDao
    companion object {
        const val DATABASE_NAME = "tb_favorite"
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteDatabase::class.java, DATABASE_NAME)
                        .build()
                }
            }
            return INSTANCE as FavoriteDatabase
        }
    }


}