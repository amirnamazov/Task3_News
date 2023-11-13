package com.example.task3.domain.use_cases

import com.example.task3.common.ResourceState
import com.example.task3.data.data_source.dto.NewsDTO.NewsDTO
import com.example.task3.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.ConnectException
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val repository: NewsRepository) {

    operator fun invoke(map: Map<String, String>): Flow<ResourceState<NewsDTO>> = flow {
        try {
            emit(ResourceState.Loading())
            val response = repository.getEverything(map)
            emit(ResourceState.Success(response))
        } catch (e: ConnectException) {
            emit(ResourceState.Error(message = "Network problem."))
        } catch (e: Exception) {
            emit(ResourceState.Error(message = e.message ?: "Something went wrong."))
        }
    }
}