package com.example.task3.di

import com.example.task3.data.data_source.remote.api.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object NewsApiModule {

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi :: class.java)
}