package com.newsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newsapp.data.remote.model.ArticlesItem

@Dao
interface NewsDao {
    @Query("Select * from news_table where category = :cat")
    suspend fun getAll(cat:String): List<ArticlesItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<ArticlesItem>)
}