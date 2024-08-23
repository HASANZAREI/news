package com.example.news.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.news.data.local.NewsDao
import com.example.news.data.remote.NewsApi
import com.example.news.data.remote.NewsPagingSource
import com.example.news.data.remote.SearchNewsPagingSource
import com.example.news.domain.model.Article
import com.example.news.domain.repository.NewsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class NewsRepoImpl(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) : NewsRepo {
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ","),
                    searchQuery = searchQuery
                )
            }
        ).flow
    }

    override suspend fun upsertArticle(article: Article) {
        newsDao.upsert(article = article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsDao.delete(article = article)
    }

    override fun selectArticles(): Flow<List<Article>> {
        return newsDao.getArticles()
    }

    override suspend fun selectArticle(url: String): Article? {
        return newsDao.getArticle(url = url)
    }
}