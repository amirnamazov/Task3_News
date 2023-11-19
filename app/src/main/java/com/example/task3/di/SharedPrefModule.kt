package com.example.task3.di

import android.content.SharedPreferences
import com.example.task3.common.SharedPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    @Provides
    @Singleton
    fun provideSharedPref(sharedPrefManager: SharedPrefManager): SharedPreferences =
        sharedPrefManager.getSharedPref()
}