package com.appsnado.momoxassignment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class SearchResponse(
    val results: List<Result>,
){
    @Parcelize
    data class Result(
        val id: String,
        val description: String?,
        val urls: Urls,
        val user: UnsplashUser
    ) : Parcelable {

        @Parcelize
        data class Urls(
            val raw: String,
            val full: String,
            val regular: String,
            val small: String,
            val thumb: String,
        ) : Parcelable

        @Parcelize
        data class UnsplashUser(
            val name: String,
            val username: String
        ) : Parcelable {
            val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
        }
    }
}