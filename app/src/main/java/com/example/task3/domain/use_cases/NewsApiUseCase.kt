package com.example.task3.domain.use_cases

import com.example.task3.common.ResponseService.flowResponse
import com.example.task3.domain.repository.NewsRemoteRepository
import javax.inject.Inject

class NewsApiUseCase @Inject constructor(private val repository: NewsRemoteRepository) {

    fun getEverything(map: Map<String, String>, delay: Long = 0) =
        flowResponse(delay) { repository.getEverything(map) }

    fun getHeadlines(map: Map<String, String>) = flowResponse {
        repository.getHeadlines(map)
    }
}