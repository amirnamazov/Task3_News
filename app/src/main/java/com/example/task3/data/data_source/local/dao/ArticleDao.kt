package com.example.task3.data.data_source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.task3.data.data_source.local.model.ArticleModel

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles_db")
    suspend fun getAll(): List<ArticleModel>

    @Insert
    suspend fun insert(article: ArticleModel)

    @Delete
    suspend fun delete(article: ArticleModel)

    @Query("DELETE FROM articles_db WHERE id = (SELECT MAX(id) FROM articles_db)")
    suspend fun deleteLast()
}