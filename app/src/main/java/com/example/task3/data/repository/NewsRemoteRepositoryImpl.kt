package com.example.task3.data.repository

import com.example.task3.data.data_source.remote.api.NewsApi
import com.example.task3.domain.repository.NewsRemoteRepository
import javax.inject.Inject

class NewsRemoteRepositoryImpl @Inject constructor(private val api: NewsApi) : NewsRemoteRepository {

    override suspend fun getEverything(map: Map<String, String>) = api.getEverything(map)

    override suspend fun getHeadlines(map: Map<String, String>) = api.getHeadlines(map)
}