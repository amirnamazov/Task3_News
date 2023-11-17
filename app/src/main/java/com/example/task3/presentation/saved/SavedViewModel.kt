package com.example.task3.presentation.saved

import androidx.lifecycle.ViewModel
import com.example.task3.domain.use_cases.NewsLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val useCase: NewsLocalUseCase) : ViewModel() {

//    private val _resNews = MutableLiveData<HomeUIState>()
//    val resNews: LiveData<HomeUIState> get() = _resNews
//
//    fun fetchNews(map: Map<String, String>) =
//        useCase.getEverything(map).fetchData(_resNews)
//
//    private fun Flow<ResourceState<NewsDTO>>.fetchData(livedata: MutableLiveData<HomeUIState>) =
//        viewModelScope.launch {
//            this@fetchData.collect { res ->
//                livedata.value = when (res) {
//                    is ResourceState.ConnectionError -> HomeUIState.ConnectionError
//                    is ResourceState.Error -> HomeUIState.Error(res.message!!)
//                    is ResourceState.Loading -> HomeUIState.Loading
//                    is ResourceState.Success ->
//                        if (res.data?.articles.isNullOrEmpty()) HomeUIState.Empty
//                        else HomeUIState.Success(res.data!!.articles!!.format())
//                }
//            }
//        }
//
//    private fun List<Article>.format(): List<Article> = map {
//        val dateFormatted = it.publishedAt?.substring(0, 10)
//        it.copy(publishedAt = dateFormatted)
//    }
}