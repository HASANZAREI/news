package com.example.news.domain.usecases.news

import com.example.news.data.local.NewsDao
import com.example.news.domain.model.Article
import com.example.news.domain.repository.NewsRepo

class SelectArticle(
    private val newsRepo: NewsRepo
) {
    suspend operator fun invoke(url: String): Article? {
        return newsRepo.selectArticle(url)
    }
}