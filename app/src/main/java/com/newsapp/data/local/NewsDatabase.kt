package com.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newsapp.data.remote.model.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}