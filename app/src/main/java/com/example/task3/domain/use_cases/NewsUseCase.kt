package com.example.task3.domain.use_cases

import com.example.task3.common.ResourceState
import com.example.task3.common.ResponseService.flowResponse
import com.example.task3.data.data_source.remote.dto.NewsDTO.NewsDTO
import com.example.task3.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val repository: NewsRepository) {

    operator fun invoke(map: Map<String, String>): Flow<ResourceState<NewsDTO>> = flowResponse {
        repository.getEverything(map)
    }
}