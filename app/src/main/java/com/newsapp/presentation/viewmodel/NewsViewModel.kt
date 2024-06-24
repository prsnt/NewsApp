package com.newsapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.newsapp.BuildConfig
import com.newsapp.data.repository.NewsRepository
import com.newsapp.data.remote.model.ArticlesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val application: Application,
    private val repository: NewsRepository
) : AndroidViewModel(application = application) {
    val newsArticle: LiveData<List<ArticlesItem>> = repository.newsArticles

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        fetchNews(
            BuildConfig.NEWS_API_KEY, "business"
        )
    }

    fun fetchNews(apikey: String, category: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                repository.fetchNews(application, apikey, category)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _loading.value = false
            }
        }
    }
}