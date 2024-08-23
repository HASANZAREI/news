package com.example.news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news.domain.model.Article

@Database(
    entities = [Article::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(NewsTypeConvertor::class)
abstract class NewsDataBase: RoomDatabase() {
    abstract val newsDao: NewsDao
}