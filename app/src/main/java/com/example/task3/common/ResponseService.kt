package com.example.task3.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.net.ConnectException

fun <T> flowResponse(request: suspend () -> Response<T>): Flow<ResourceState<T>> = flow {
    try {
        emit(ResourceState.Loading())
        val response = request.invoke()
        if (response.isSuccessful && response.body() != null) {
            emit(ResourceState.Success(response.body()!!))
        } else {
            emit(ResourceState.Error(message = response.message()))
        }
    } catch (e: ConnectException) {
        emit(ResourceState.ConnectionError(message = "Connection Error."))
    } catch (e: Exception) {
        emit(ResourceState.Error(message = e.message ?: "Something went wrong."))
    }
}