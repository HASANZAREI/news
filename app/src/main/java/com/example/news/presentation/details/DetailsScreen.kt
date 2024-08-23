package com.example.news.presentation.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news.R
import com.example.news.domain.model.Article
import com.example.news.domain.model.Source
import com.example.news.presentation.DetailsEvent
import com.example.news.presentation.Dimens
import com.example.news.presentation.details.components.DetailsTopBar
import com.example.news.ui.theme.NewsTheme

@SuppressLint("QueryPermissionsNeeded")
@Composable
fun DetailsScreen(
    article: Article,
    onEvent: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = Uri.parse(article.url)
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, article.url)
                    it.type = "text/plain"
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onBookmarkClick = { onEvent(DetailsEvent.UpsertDeleteArticle(article = article)) },
            onBackClick = navigateUp
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                start = Dimens.MediumPadding1,
                end = Dimens.MediumPadding1,
                top = Dimens.MediumPadding1
            )
        ) {
            item {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(248.dp)
                        .clip(MaterialTheme.shapes.large),
                    model = ImageRequest.Builder(context).data(article.urlToImage).build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(Dimens.MediumPadding1))
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(
                        id = R.color.text_title
                    )
                )
                Spacer(modifier = Modifier.height(Dimens.MediumPadding1))
                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(
                        id = R.color.body
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsScreenPreview() {
    NewsTheme {
        DetailsScreen(
            article = Article(
                "Jessica Klein",
                "Trumps speech is an hour behind. A half hour into the wait, restless attendees start chanting Trump. The woman sitting in front of me murmurs her own chant: Bitcoin, bitcointhats what they should be… [+3147 chars]",
                "At Bitcoin 2024 conference in Nashville, Trump is finally telling crypto enthusiasts what they want to hear.",
                "2024-07-28T12:43:07Z",
                Source("wired", "Wired"),
                "Bitcoin Bros Go Wild for Donald Trump",
                "https://www.wired.com/story/bitcoin-bros-go-wild-for-donald-trump/",
                "https://media.wired.com/photos/66a56f21bf2909f08a634953/191:100/w_1280,c_limit/Crypto-Bros-Business-2162975355.jpg"
            ), onEvent = {}) {
        }
    }

}