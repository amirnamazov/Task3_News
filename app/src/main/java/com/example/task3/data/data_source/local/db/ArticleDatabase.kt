package com.example.task3.data.data_source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.task3.data.data_source.local.dao.ArticleDao
import com.example.task3.data.data_source.local.model.ArticleModel
import com.example.task3.data.data_source.local.model.SourceTypeConverter

@Database(entities = [ArticleModel::class], version = 1)
@TypeConverters(SourceTypeConverter :: class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun userDao(): ArticleDao
}