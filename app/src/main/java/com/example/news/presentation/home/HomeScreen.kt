package com.example.news.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.example.news.R
import com.example.news.domain.model.Article
import com.example.news.presentation.Dimens
import com.example.news.presentation.common.ArticleList
import com.example.news.presentation.common.SearchBar


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Article) -> Unit
) {
    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = " \uD83d\uDFE5 ") { it.title }
            } else {
                ""
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Dimens.MediumPadding1)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(150.dp),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.size(Dimens.MediumPadding1))
        SearchBar(
            text = "",
            readOnly = true,
            onValueChange = {},
            onSearch = {},
            onClick = { navigateToSearch() })
        Spacer(modifier = Modifier.size(Dimens.MediumPadding1))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimens.MediumPadding1)
                .basicMarquee(),
            text = titles,
            fontSize = 20.sp,
            color = colorResource(id = R.color.placeholder)
        )
        Spacer(modifier = Modifier.size(Dimens.MediumPadding1))
        ArticleList(
            modifier = Modifier.padding(horizontal = Dimens.MediumPadding1),
            articles = articles,
            onClick = { navigateToDetails(it) }
        )
    }
}