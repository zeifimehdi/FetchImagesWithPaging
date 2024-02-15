package com.appsnado.momoxassignment.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.liveData
import com.appsnado.momoxassignment.api.ApiServices
import com.appsnado.momoxassignment.model.SearchResponse
import com.appsnado.momoxassignment.paging.ImagesPagingSource
import com.appsnado.momoxassignment.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ApiRepository @Inject constructor(
    private val apiServices: ApiServices,
) :ApiRepositoryInterface {

    override fun getImageList(
        searchKeyword: String
    ) =
            Pager(
                config = PagingConfig(
                    pageSize = 10,
                    maxSize = 30,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { ImagesPagingSource(apiServices, searchKeyword) }
            ).liveData


}