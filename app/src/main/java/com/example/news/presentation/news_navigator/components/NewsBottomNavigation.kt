package com.example.news.presentation.news_navigator.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.news.R
import com.example.news.presentation.Dimens.IconSize


@Composable
fun NewsBottomNavigation(
    items: List<BottomNavItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {
    // Implement your Bottom Navigation UI here
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 14.dp, topStart = 14.dp)),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 10.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedItem,
                onClick = { onItemClick(index) },
                icon = {
                    Icon(
                        modifier = Modifier
                            .size(IconSize),
                        painter = painterResource(id = item.icon),
                        contentDescription = ""
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = colorResource(id = R.color.body),
                    unselectedTextColor = colorResource(id = R.color.body),
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}

data class BottomNavItem(
    @DrawableRes val icon: Int,
    val text: String
)

@Preview
@Composable
fun NewsBottomNavigationPreview() {
    NewsBottomNavigation(
        onItemClick = {},
        selectedItem = 0,
        items = listOf(
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
    )
}