package com.appsnado.momoxassignment.viewModel

import androidx.lifecycle.*
import androidx.paging.*
import com.appsnado.momoxassignment.model.SearchResponse
import com.appsnado.momoxassignment.paging.ImagesPagingSource
import com.appsnado.momoxassignment.repository.ApiRepository
import com.appsnado.momoxassignment.repository.ApiRepositoryInterface
import com.appsnado.momoxassignment.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val apiRepositoryInterface: ApiRepositoryInterface,
     state : SavedStateHandle
) : ViewModel(){
    private val currentQuery = state.getLiveData(CURRENT_SEARCH, DEFAULT_SEARCH)

    val image =
        currentQuery.switchMap {
            apiRepositoryInterface.getImageList(it).cachedIn(viewModelScope) }

    fun searchImage(query: String){
        currentQuery.value = query

    }

    companion object{
        private const val DEFAULT_SEARCH = "current_query"
        private const val CURRENT_SEARCH = "Flowers"
    }
}