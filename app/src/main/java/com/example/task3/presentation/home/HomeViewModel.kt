package com.example.task3.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task3.common.ResourceState
import com.example.task3.data.data_source.remote.dto.news.NewsDTO
import com.example.task3.domain.model.Article
import com.example.task3.domain.use_cases.NewsApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: NewsApiUseCase) : ViewModel() {

    private val _resHeadlines = MutableLiveData<HomeUIState>()
    val resHeadlines: LiveData<HomeUIState> get() = _resHeadlines

    private val _resNews = MutableLiveData<HomeUIState>()
    val resNews: LiveData<HomeUIState> get() = _resNews

    fun fetchHeadlines(map: Map<String, String>) =
        useCase.getHeadlines(map).fetchData(_resHeadlines)

    fun fetchNews(map: Map<String, String>) =
        useCase.getEverything(map).fetchData(_resNews)

    private fun Flow<ResourceState<NewsDTO>>.fetchData(livedata: MutableLiveData<HomeUIState>) =
        viewModelScope.launch {
            this@fetchData.collect { res ->
                livedata.value = when (res) {
                    is ResourceState.ConnectionError -> HomeUIState.ConnectionError
                    is ResourceState.Error -> HomeUIState.Error(res.message!!)
                    is ResourceState.Loading -> HomeUIState.Loading
                    is ResourceState.Success ->
                        if (res.data?.articles.isNullOrEmpty()) HomeUIState.Empty
                        else HomeUIState.Success(res.data!!.articles!!.format())
                }
            }
        }

    private fun List<Article>.format(): List<Article> = map {
        val dateFormatted = it.publishedAt?.substring(0, 10)
        it.copy(publishedAt = dateFormatted)
    }
}