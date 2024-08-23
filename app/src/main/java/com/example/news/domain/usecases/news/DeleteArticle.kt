package com.example.news.domain.usecases.news

import com.example.news.data.local.NewsDao
import com.example.news.domain.model.Article
import com.example.news.domain.repository.NewsRepo

class DeleteArticle(
    private val newsRepo: NewsRepo
) {
    suspend operator fun invoke(article: Article) = newsRepo.deleteArticle(article)
}