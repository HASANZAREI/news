package com.example.news.presentation.nvgraph

sealed class Route(
    val route: String
) {
    object OnBoardingScreen : Route("onboarding")
    object HomeScreen : Route("home")
    object DetailScreen : Route("detail")
    object SearchScreen : Route("search")
    object BookMarkScreen : Route("bookmark")
    object AppStartNavigation : Route("app_start_navigation")
    object NewsNavigation : Route("news_navigation")
    object NewsNavigator : Route("news_navigator")
}