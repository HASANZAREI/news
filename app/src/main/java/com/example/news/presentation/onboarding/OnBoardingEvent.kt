package com.example.news.presentation.onboarding

sealed class OnBoardingEvent {
    object saveAppEntry : OnBoardingEvent()
}