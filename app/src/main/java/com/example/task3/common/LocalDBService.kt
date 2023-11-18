package com.example.task3.common

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.ConnectException

object LocalDBService {

    fun <T> flowResponse(request: suspend () -> T): Flow<ResourceState<T>> = flow {
        try {
            emit(ResourceState.Loading())
            delay(300)
            val response = request.invoke()
            emit(ResourceState.Success(response))
        } catch (e: ConnectException) {
            emit(ResourceState.ConnectionError(message = "Connection Problem."))
        } catch (e: Exception) {
            emit(ResourceState.Error(message = e.message ?: "Something went wrong."))
        }
    }
}