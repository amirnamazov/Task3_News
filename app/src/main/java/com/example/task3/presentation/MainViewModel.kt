package com.example.task3.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task3.common.ResourceState
import com.example.task3.domain.use_cases.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: NewsUseCase) : ViewModel() {

    private val _responseNews = MutableLiveData<String>()

    val responseNews: LiveData<String> get() = _responseNews

    fun fetchNews(map: Map<String, String>) = viewModelScope.launch(Dispatchers.Main) {

        useCase(map).collect { res ->
            _responseNews.value = when(res) {
                is ResourceState.Error -> res.message
                is ResourceState.Loading -> "loading"
                is ResourceState.Success -> res.data!!.articles[0].description
            }
        }
    }
}