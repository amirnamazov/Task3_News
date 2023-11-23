package com.example.task3.data.repository

import android.content.SharedPreferences
import com.example.task3.data.data_source.remote.api.NewsApi
import com.example.task3.domain.repository.NewsRemoteRepository
import javax.inject.Inject

class NewsRemoteRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val sharedPref: SharedPreferences
) : NewsRemoteRepository {

    override suspend fun getEverything(map: Map<String, String>) = api.getEverything(map)

    override suspend fun getHeadlines(map: Map<String, String>) = api.getHeadlines(map)

    override fun getLangValue(): String = sharedPref.getString("LANGUAGE", "en")!!

    override fun setLangValue(value: String) = sharedPref.edit().putString("LANGUAGE", value).apply()
}