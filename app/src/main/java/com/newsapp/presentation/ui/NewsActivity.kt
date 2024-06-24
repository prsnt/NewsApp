package com.newsapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.newsapp.data.remote.model.ArticlesItem
import com.newsapp.data.local.model.Category
import com.newsapp.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }

    @Composable
    fun NewsCard(article: ArticlesItem) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(), elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                article.title?.let { Text(text = it, style = MaterialTheme.typography.h6) }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = article.description ?: "", style = MaterialTheme.typography.body2)
            }
        }
    }

    @Composable
    fun NewsList(viewModel: NewsViewModel) {
        var articles by remember { mutableStateOf<List<ArticlesItem>>(emptyList()) }
        val lifecycleOwner = LocalLifecycleOwner.current

        val isLoading by viewModel.loading.collectAsState()

        DisposableEffect(lifecycleOwner) {
            val observer = Observer<List<ArticlesItem>> { newArticles ->
                articles = newArticles
            }
            viewModel.newsArticle.observe(lifecycleOwner, observer)
            onDispose {
                viewModel.newsArticle.removeObserver(observer)
            }
        }
        if (isLoading) {
            // Show loading indicator while data is being fetched
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Display the list of articles or "No data found" message
            if (articles.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No data found")
                }
            } else {
                LazyColumn {
                    items(articles) { article ->
                        NewsCard(article = article)
                    }
                }
            }
        }
    }

    @Composable
    fun CategoryItem(category: Category, selected: Boolean, onItemClick: (Category) -> Unit) {
        val backgroundColor = if (selected) {
            Color.LightGray
        } else {
            Color.White
        }
        Card(
            backgroundColor = backgroundColor,
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    onItemClick(category)
                }
                .fillMaxWidth(), elevation = 4.dp

        ) {
            Text(
                text = category.name,
                style = TextStyle(color = Color.Black, fontSize = 16.sp),
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }


    @Composable
    fun CategoryList(
        categories: List<Category>,
        selectedCategory: Category?,
        onItemClick: (Category) -> Unit
    ) {
        LazyRow {
            items(categories) { category ->
                CategoryItem(
                    category = category,
                    selected = category == selectedCategory,
                    onItemClick = onItemClick
                )
            }
        }
    }

    @Composable
    fun MyApp() {

        val categories = remember {
            listOf(
                Category("business", "Business"),
                Category("entertainment", "Entertainment"),
                Category("general", "General"),
                Category("health", "Health"),
                Category("science", "Science"),
                Category("sports", "Sports"),
                Category("technology", "Technology"),
            )
        }

        var selectedCategory by remember { mutableStateOf<Category?>(categories[0]) }

        MaterialTheme {
            Surface(color = MaterialTheme.colors.background) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    CategoryList(categories = categories, selectedCategory) { category ->
                        selectedCategory = category
                        selectedCategory?.query_name?.let {
                            newsViewModel.fetchNews("2d6ea8a1bad248f59f0de4479e4c881d", it)
                        }
                    }
                    NewsList(viewModel = newsViewModel)
                }
            }
        }
    }
}