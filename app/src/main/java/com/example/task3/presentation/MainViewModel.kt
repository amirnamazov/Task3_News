package com.example.task3.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task3.data.data_source.dto.NewsDTO.NewsDTO
import com.example.task3.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _responseNews = MutableLiveData<Response<NewsDTO>>()

    val responseNews: LiveData<Response<NewsDTO>> get() = _responseNews

//    private val errorHandler by lazy {
//        CoroutineExceptionHandler { _, throwable ->
//
//        }
//    }

//    fun fetchNews(map: Map<String, String>) = viewModelScope.launch {
//        val response = async { MainRepository().getEverything(map) }.await()
//        println("dlsbfodiodifv   ${response.body()}")
//    }

    fun fetchNews(map: Map<String, String>) = viewModelScope.launch {
        val response = async { repository.getEverything(map) }.await()
        _responseNews.value = response
    }
}