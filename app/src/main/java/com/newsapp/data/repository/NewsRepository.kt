package com.newsapp.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newsapp.data.local.NewsDao
import com.newsapp.data.remote.model.ArticlesItem
import com.newsapp.data.remote.NewsApiService
import com.newsapp.util.isNetworkAvailable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsDao: NewsDao
) {
    private val _newsArticles = MutableLiveData<List<ArticlesItem>>()
    val newsArticles: LiveData<List<ArticlesItem>> get() = _newsArticles


    suspend fun fetchNews(context: Context, apikey: String, category: String) {
        if (isNetworkAvailable(context)) {
            val response = newsApiService.getNews(apikey, category)
            if (response.isSuccessful) {
                _newsArticles.postValue(response.body()?.articles ?: emptyList())
                response.body()?.articles?.let {
                    val updatedArticles = it.map { articlesItem ->
                        articlesItem.category = category
                        articlesItem
                    }
                    newsDao.insertAll(updatedArticles)
                }
            } else {
                _newsArticles.postValue(newsDao.getAll(category))
            }
        } else
            _newsArticles.postValue(newsDao.getAll(category))
    }
}