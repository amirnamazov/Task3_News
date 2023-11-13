package com.example.task3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task3.model.News
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel() {

//    private val errorHandler by lazy {
//        CoroutineExceptionHandler { _, throwable ->
//
//        }
//    }

//    fun fetchNews(map: Map<String, String>) = viewModelScope.launch {
//        val response = async { MainRepository().getEverything(map) }.await()
//        println("dlsbfodiodifv   ${response.body()}")
//    }

    fun fetchNews(map: Map<String, String>): Flow<Response<News>> = flow {
        viewModelScope.launch {
            val response = async { MainRepository().getEverything(map) }.await()
            emit(response)
        }
    }
}