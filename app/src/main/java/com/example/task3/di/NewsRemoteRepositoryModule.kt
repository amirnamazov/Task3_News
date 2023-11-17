package com.example.task3.di

import com.example.task3.data.repository.NewsRemoteRepositoryImpl
import com.example.task3.domain.repository.NewsRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent :: class)
interface NewsRemoteRepositoryModule {

    @Binds
    fun bindsNewsRepository(repository: NewsRemoteRepositoryImpl): NewsRemoteRepository
}