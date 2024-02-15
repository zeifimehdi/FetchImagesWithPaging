package com.appsnado.momoxassignment.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.appsnado.momoxassignment.model.SearchResponse
import com.appsnado.momoxassignment.utils.Resource

interface ApiRepositoryInterface {
      fun getImageList(searchKeyword: String): LiveData<PagingData<SearchResponse.Result>>
}