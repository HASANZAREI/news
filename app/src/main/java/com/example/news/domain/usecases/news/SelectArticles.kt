package com.example.news.domain.usecases.news

import com.example.news.data.local.NewsDao
import com.example.news.domain.model.Article
import com.example.news.domain.repository.NewsRepo
import kotlinx.coroutines.flow.Flow

class SelectArticles(
    private val newsRepo: NewsRepo
) {
    operator fun invoke(): Flow<List<Article>> {
        return newsRepo.selectArticles()
    }
}