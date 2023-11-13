package com.example.task3.di

import com.example.task3.data.repository.NewsRepositoryImpl
import com.example.task3.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent :: class)
abstract class NewsRepositoryModule {

    @Binds
    abstract fun bindsNewsRepository(repository: NewsRepositoryImpl): NewsRepository
}