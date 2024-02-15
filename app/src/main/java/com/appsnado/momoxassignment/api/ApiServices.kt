package com.appsnado.momoxassignment.api

import com.appsnado.momoxassignment.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("/search/photos")
    suspend fun getSearchedImageList(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("query") query: String,

    ):SearchResponse
}