package com.example.task3.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task3.common.ResourceState
import com.example.task3.data.data_source.local.model.ArticleModel
import com.example.task3.domain.model.Article
import com.example.task3.domain.use_cases.NewsLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val useCase: NewsLocalUseCase) : ViewModel() {

    private val _articleSave = MutableLiveData<DetailsState>()
    val articleSave: LiveData<DetailsState> get() = _articleSave

    private val _articleRemove = MutableStateFlow<DetailsState>(DetailsState.Loading)
    val articleRemove: StateFlow<DetailsState> get() = _articleRemove

    fun saveArticle(article: Article) {
        val model = ArticleModel.toModel(article)
        useCase.insert(model).getResponse(_articleSave)
    }

//    fun removeArticle(article: Article) {
//        val model = ArticleModel.toModel(article)
//        useCase.delete(model).getResponse(_articleSave)
//    }

    private fun Flow<ResourceState<Unit>>.getResponse(liveData: MutableLiveData<DetailsState>) =
        viewModelScope.launch {
            this@getResponse.collect { res ->
                liveData.value = when (res) {
                    is ResourceState.ConnectionError -> DetailsState.Error(res.message!!)
                    is ResourceState.Error -> DetailsState.Error(res.message!!)
                    is ResourceState.Loading -> DetailsState.Loading
                    is ResourceState.Success -> DetailsState.Success()
                }
            }
        }
}