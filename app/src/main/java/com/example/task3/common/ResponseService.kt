package com.example.task3.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import retrofit2.Response
import java.net.ConnectException

object ResponseService {

    fun <T> flowResponse(request: suspend () -> Response<T>): Flow<ResourceState<T>> = channelFlow {
        try {
            send(ResourceState.Loading())
            val response = request.invoke()
            if (response.isSuccessful && response.body() != null) {
                send(ResourceState.Success(response.body()!!))
            } else {
                send(ResourceState.Error(message = response.message()))
            }
        } catch (e: ConnectException) {
            send(ResourceState.ConnectionError(message = "Connection Error."))
        } catch (e: Exception) {
            send(ResourceState.Error(message = e.message ?: "Something went wrong."))
        }
    }
}