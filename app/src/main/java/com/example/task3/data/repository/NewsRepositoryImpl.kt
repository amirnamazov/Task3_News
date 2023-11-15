package com.example.task3.data.repository

import com.example.task3.data.data_source.remote.api.NewsApi
import com.example.task3.data.data_source.remote.dto.NewsDTO.NewsDTO
import com.example.task3.domain.repository.NewsRepository
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val api: NewsApi) : NewsRepository {

    override suspend fun getEverything(map: Map<String, String>): Response<NewsDTO> =
        api.getEverything(map)
}