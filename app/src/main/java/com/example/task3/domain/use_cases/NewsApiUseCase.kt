package com.example.task3.domain.use_cases

import com.example.task3.common.ResourceState
import com.example.task3.common.ResponseService.flowResponse
import com.example.task3.data.data_source.remote.dto.news.NewsDTO
import com.example.task3.domain.repository.NewsRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsApiUseCase @Inject constructor(private val repository: NewsRemoteRepository) {

    fun getEverything(map: Map<String, String>): Flow<ResourceState<NewsDTO>> = flowResponse {
        repository.getEverything(map)
    }

    fun getHeadlines(map: Map<String, String>): Flow<ResourceState<NewsDTO>> = flowResponse {
        repository.getHeadlines(map)
    }
}