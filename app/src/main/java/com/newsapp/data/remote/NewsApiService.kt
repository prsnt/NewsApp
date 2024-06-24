package com.newsapp.data.remote

import com.newsapp.data.remote.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("apiKey") apikey: String,
        @Query("category") category: String
    ): Response<NewsResponse>
}