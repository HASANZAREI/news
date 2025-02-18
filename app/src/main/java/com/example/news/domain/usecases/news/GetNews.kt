package com.example.news.domain.usecases.news

import androidx.paging.PagingData
import com.example.news.domain.model.Article
import com.example.news.domain.repository.NewsRepo
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepo: NewsRepo
) {
    operator fun invoke(
        sources: List<String>
    ): Flow<PagingData<Article>> {
        return newsRepo.getNews(sources = sources)
    }
}