package com.example.news.domain.usecases.news

import androidx.paging.PagingData
import com.example.news.domain.model.Article
import com.example.news.domain.repository.NewsRepo
import kotlinx.coroutines.flow.Flow

class SearchNews(
    private val newsRepo: NewsRepo
) {
    operator fun invoke(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return newsRepo.searchNews(searchQuery = searchQuery, sources = sources)
    }
}