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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.properties.Delegates

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: NewsApiUseCase) : ViewModel() {

    fun getLangValue(): String = useCase.getLangValue()

    fun setLangValue(value: String) = useCase.setLangValue(value)

    private fun mapEveryThing(query: String): Map<String, String> = mapOf(
        "q" to query,
        "language" to getLangValue()
    )

    private val _resHeadlines = MutableLiveData<HomeUIState>()
    val resHeadlines: LiveData<HomeUIState> get() = _resHeadlines

    fun fetchHeadlines() = useCase.getHeadlines().fetchData(_resHeadlines)

    private val _resNews = MutableLiveData<HomeUIState>()
    val resNews: LiveData<HomeUIState> get() = _resNews

    fun fetchNews(query: String = "tesla") =
        useCase.getEverything(mapEveryThing(query)).fetchData(_resNews)

    private val sharedFlowSearch = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).apply {
        viewModelScope.launch(Dispatchers.IO) {
            debounce(300).collect { fetchNews(it) }
        }
    }

    var searchText by Delegates.observable("") { _, old, new ->
        if (new != old && new.isNotEmpty()) sharedFlowSearch.tryEmit(new)
    }

    private fun Flow<ResourceState<NewsDTO>>.fetchData(livedata: MutableLiveData<HomeUIState>) =
        viewModelScope.launch(Dispatchers.IO) {
            this@fetchData.collect { res ->
                withContext(Dispatchers.Main) {
                    livedata.value = when (res) {
                        is ResourceState.ConnectionError -> HomeUIState.ConnectionError(res.message!!)
                        is ResourceState.Error -> HomeUIState.Error(res.message!!)
                        is ResourceState.Loading -> HomeUIState.Loading
                        is ResourceState.Success ->
                            if (res.data?.articles.isNullOrEmpty()) HomeUIState.Empty
                            else HomeUIState.Success(res.data!!.articles!!.format())
                    }
                }
            }
        }

    private fun List<Article>.format(): List<Article> = map {
        val dateFormatted = it.publishedAt?.substring(0, 10)
        it.copy(publishedAt = dateFormatted)
    }
}