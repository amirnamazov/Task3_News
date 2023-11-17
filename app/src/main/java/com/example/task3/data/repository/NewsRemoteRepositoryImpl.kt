package com.example.task3.data.repository

import com.example.task3.data.data_source.remote.api.NewsApi
import com.example.task3.data.data_source.remote.dto.news.NewsDTO
import com.example.task3.domain.repository.NewsRemoteRepository
import retrofit2.Response
import javax.inject.Inject

class NewsRemoteRepositoryImpl @Inject constructor(private val api: NewsApi) : NewsRemoteRepository {

    override suspend fun getEverything(map: Map<String, String>): Response<NewsDTO> =
        api.getEverything(map)

    override suspend fun getHeadlines(map: Map<String, String>): Response<NewsDTO> =
        api.getHeadlines(map)
}