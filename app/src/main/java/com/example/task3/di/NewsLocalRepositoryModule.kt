package com.example.task3.di

import com.example.task3.data.repository.NewsLocalRepositoryImpl
import com.example.task3.domain.repository.NewsLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent :: class)
interface NewsLocalRepositoryModule {

    @Binds
    fun bindsNewsRepository(repository: NewsLocalRepositoryImpl): NewsLocalRepository
}