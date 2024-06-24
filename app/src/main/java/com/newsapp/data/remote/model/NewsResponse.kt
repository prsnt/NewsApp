package com.newsapp.data.remote.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Parcelize
data class NewsResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem>? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Entity(tableName = "news_table")
@Parcelize
data class ArticlesItem(

	var category: String? = null,

	@PrimaryKey
	@field:SerializedName("publishedAt")
	val publishedAt: String,

	@field:SerializedName("author")
	val author: String? = "",

	@field:SerializedName("urlToImage")
	val urlToImage: String? = "",

	@field:SerializedName("description")
	val description: String? = "",

	@field:SerializedName("title")
	val title: String? = "",

	@field:SerializedName("url")
	val url: String? = "",

	@field:SerializedName("content")
	val content: String? = null
) : Parcelable

@Parcelize
data class Source(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable
