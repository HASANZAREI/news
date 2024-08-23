package com.example.news.presentation

import com.example.news.domain.model.Article

sealed class DetailsEvent {
    data class UpsertDeleteArticle(val article: Article) : DetailsEvent()
    object RemoveSideEffect : DetailsEvent()
}