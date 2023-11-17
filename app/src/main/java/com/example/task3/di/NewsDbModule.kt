package com.example.task3.di

import android.content.Context
import androidx.room.Room
import com.example.task3.data.data_source.local.dao.ArticleDao
import com.example.task3.data.data_source.local.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsDbModule {

    @Provides
    @Singleton
    fun providesDB(@ApplicationContext context: Context): ArticleDatabase =
        Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            "articles_db"
        ).build()

    @Provides
    @Singleton
    fun providesDao(db: ArticleDatabase): ArticleDao = db.userDao()
}