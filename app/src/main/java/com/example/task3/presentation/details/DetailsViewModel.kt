package com.example.task3.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task3.common.ResourceState
import com.example.task3.data.data_source.local.model.ArticleModel
import com.example.task3.domain.use_cases.NewsLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val useCase: NewsLocalUseCase) : ViewModel() {

    suspend fun checkExistenceInDb(model: ArticleModel, saved: (Boolean) -> Unit) = useCase.getAll()
        .filter { it is ResourceState.Success }
        .collect { res ->
            val articles = res.data!!.map { it.article }
            saved(articles.contains(model.article))
        }

    private val _articleSave = MutableLiveData<DetailsState>()
    val articleSave: LiveData<DetailsState> get() = _articleSave

    fun saveArticle(model: ArticleModel) = viewModelScope.launch(Dispatchers.IO) {
        checkExistenceInDb(model) { saved ->
            if (!saved) useCase.insert(model).getResponse(_articleSave)
        }
    }

    private val _articleRemove = MutableLiveData<DetailsState>()
    val articleRemove: LiveData<DetailsState> get() = _articleRemove

    fun removeArticle(id: Int) =
        if (id != 0) useCase.delete(id).getResponse(_articleRemove)
        else useCase.deleteLast().getResponse(_articleRemove)

    private fun Flow<ResourceState<Unit>>.getResponse(liveData: MutableLiveData<DetailsState>) =
        viewModelScope.launch(Dispatchers.IO) {
            this@getResponse.collect { res ->
                withContext(Dispatchers.Main) {
                    liveData.value = when (res) {
                        is ResourceState.ConnectionError -> DetailsState.Error(res.message!!)
                        is ResourceState.Error -> DetailsState.Error(res.message!!)
                        is ResourceState.Loading -> null
                        is ResourceState.Success -> DetailsState.Success()
                    }
                }
            }
        }
}