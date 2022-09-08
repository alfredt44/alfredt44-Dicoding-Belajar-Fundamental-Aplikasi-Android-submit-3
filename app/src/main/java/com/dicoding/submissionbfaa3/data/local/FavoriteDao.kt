package com.dicoding.submissionbfaa3.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.submissionbfaa3.data.model.User

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM tb_favorite")
    fun getListFavoriteProvider(): Cursor

    @Query("SELECT * FROM tb_favorite ORDER BY login ASC ")
    fun getListFavorite(): List<User>

    @Query("SELECT COUNT(id) FROM tb_favorite WHERE id = :id")
    fun checkFavorite(id: Int): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(data: User) : Long

    @Delete
    fun deleteFavorite(vararg favorite: User) : Int

    @Query("DELETE FROM " + "tb_favorite" + " WHERE " + "id" + " = :id")
    fun deleteById(id: Long): Int
}