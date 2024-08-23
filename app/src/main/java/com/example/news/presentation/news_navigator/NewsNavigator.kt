package com.example.news.presentation.news_navigator

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.R
import com.example.news.domain.model.Article
import com.example.news.presentation.DetailsEvent
import com.example.news.presentation.bookmark.BookmarkScreen
import com.example.news.presentation.bookmark.BookmarkViewModel
import com.example.news.presentation.details.DetailsScreen
import com.example.news.presentation.details.DetailsViewModel
import com.example.news.presentation.home.HomeScreen
import com.example.news.presentation.home.HomeViewModel
import com.example.news.presentation.news_navigator.components.BottomNavItem
import com.example.news.presentation.news_navigator.components.NewsBottomNavigation
import com.example.news.presentation.nvgraph.Route
import com.example.news.presentation.search.SearchScreen
import com.example.news.presentation.search.SearchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsNavigator() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavItem(
                icon = R.drawable.ic_home,
                text = "Home"
            ),
            BottomNavItem(
                icon = R.drawable.ic_search,
                text = "Search"
            ),
            BottomNavItem(
                icon = R.drawable.ic_bookmark,
                text = "Bookmark"
            )
        )
    }
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookMarkScreen.route -> 2
            else -> 0
        }
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
        backStackState?.destination?.route == Route.SearchScreen.route ||
        backStackState?.destination?.route == Route.BookMarkScreen.route
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selectedItem = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                tab = Route.HomeScreen.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                tab = Route.SearchScreen.route
                            )

                            2 -> navigateToTab(
                                navController = navController,
                                tab = Route.BookMarkScreen.route
                            )

                            else -> Unit
                        }
                    }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            modifier = Modifier
                .padding(bottom = bottomPadding),
            navController = navController,
            startDestination = Route.HomeScreen.route
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            tab = Route.SearchScreen.route
                        )
                    },
                    navigateToDetails = { article ->
                        navigateToDetails(navController = navController, article = article)
                    }
                )
            }

            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(navController = navController, article = article)
                    }
                )
            }

            composable(route = Route.DetailScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                val context = LocalContext.current
                if (viewModel.sideEffect != null) {
                    Toast.makeText(context, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")?.let { article ->
                    DetailsScreen(article = article, onEvent = viewModel::onEvent, navigateUp = {navController.navigateUp()})
                }
            }

            composable(route = Route.BookMarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(
                    state = state,
                    navigate = { article ->
                        navigateToDetails(navController = navController, article = article)
                    }
                )
            }
        }
    }
}

private fun navigateToTab(navController: NavController, tab: String) {
    navController.navigate(tab) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) { saveState = true }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(Route.DetailScreen.route)
}