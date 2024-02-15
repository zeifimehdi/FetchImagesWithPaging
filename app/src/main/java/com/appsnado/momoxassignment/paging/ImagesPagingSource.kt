package com.appsnado.momoxassignment.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.appsnado.momoxassignment.api.ApiServices
import com.appsnado.momoxassignment.model.SearchResponse
import com.appsnado.momoxassignment.repository.ApiRepository
import com.appsnado.momoxassignment.repository.ApiRepositoryInterface
import retrofit2.HttpException
import javax.inject.Inject

class ImagesPagingSource(
    private val apiServices: ApiServices,
    private val searchedString:String) : PagingSource<Int,SearchResponse.Result>()
 {
     override fun getRefreshKey(state: PagingState<Int, SearchResponse.Result>): Int? {
         return null
//         return state.anchorPosition?.let { anchorPosition ->
//             val anchorPage = state.closestPageToPosition(anchorPosition)
//             anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        // }
     }

     override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResponse.Result> {
         return try {
             val position = params.key ?: 1
             val response = apiServices.getSearchedImageList(position,10,searchedString)

             return response.results.let {
                 LoadResult.Page(
                     data = it,
                     prevKey = if (position == 1) null else position - 1,
                     nextKey = if (it.isEmpty()) null else position + 1
                 )
             }
         } catch (e: Exception) {
             LoadResult.Error(e)
         } catch (httpException : HttpException){
             LoadResult.Error(httpException)
         }
     }
 }