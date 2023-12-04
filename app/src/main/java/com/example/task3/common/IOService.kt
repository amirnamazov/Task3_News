package com.example.task3.common

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.net.ConnectException

object IOService {

    fun <T> flowData(request: suspend () -> T): Flow<ResourceState<T>> = flow {
        emit(ResourceState.Loading())
        delay(200)
        val response = request.invoke()
        emit(ResourceState.Success(response))
    }.catch {e ->
        when(e) {
            is ConnectException -> emit(ResourceState.ConnectionError(message = "Connection problem."))
            else -> emit(ResourceState.Error(message = e.message ?: "Something went wrong."))
        }
    }

    fun <T> flowResponse(request: suspend () -> Response<T>): Flow<ResourceState<T & Any>> = flow {
        emit(ResourceState.Loading())
        val response = request.invoke()
        if (response.isSuccessful && response.body() != null) {
            emit(ResourceState.Success(response.body()!!))
        } else {
            emit(ResourceState.Error(message = response.message()))
        }
    }.catch {e ->
        when(e) {
            is ConnectException -> emit(ResourceState.ConnectionError(message = "Network connection problem."))
            else -> emit(ResourceState.Error(message = e.message ?: "Something went wrong."))
        }
    }
}