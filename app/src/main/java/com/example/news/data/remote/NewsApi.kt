package com.example.news.data.remote

import com.example.news.data.remote.dto.NewsResponse
import com.example.news.util.Constans
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String = "bbc-news",
        @Query("apiKey") apiKey: String = Constans.API_KEY
    ) : NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = Constans.API_KEY
    ) : NewsResponse
}