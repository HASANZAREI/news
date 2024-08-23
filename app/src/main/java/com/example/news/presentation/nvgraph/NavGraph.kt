package com.example.news.presentation.nvgraph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.presentation.bookmark.BookmarkScreen
import com.example.news.presentation.bookmark.BookmarkViewModel
import com.example.news.presentation.home.HomeScreen
import com.example.news.presentation.home.HomeViewModel
import com.example.news.presentation.news_navigator.NewsNavigator
import com.example.news.presentation.onboarding.OnBoardingScreen
import com.example.news.presentation.onboarding.OnBoardingViewModel
import com.example.news.presentation.search.SearchScreen
import com.example.news.presentation.search.SearchViewModel

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(event =  viewModel::onEvent)
            }
        }
        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigator.route
        ) {
            composable(Route.NewsNavigator.route) {
                NewsNavigator()
            }
        }
    }
}