package com.wandroid.app.page.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.wandroid.app.R
import com.wandroid.app.ext.navigation.BaseEmptyArgNavPath
import com.wandroid.app.page.home.business.HomeArticle
import com.wandroid.app.page.home.business.HomeViewModel
import com.wandroid.app.ui.widget.AuthorTitle
import com.wandroid.app.ui.widget.DescTitle
import com.wandroid.app.ui.widget.ItemTitle
import com.wandroid.app.ui.widget.drawBackground
import com.wandroid.app.util.getString
import com.wandroid.app.util.inColor
import com.wandroid.app.util.inDp
import com.wandroid.app.util.inString
import com.wandroid.app.util.px
import kotlinx.coroutines.CoroutineScope

object HomeNav : BaseEmptyArgNavPath() {
    override val path: String
        get() = "home"
}

@Composable
fun HomePage(
    controller: NavHostController,
    entry: NavBackStackEntry,
    i: HomeViewModel = viewModel(HomeViewModel::class),
    scope: CoroutineScope = rememberCoroutineScope(),
) {

    val article by i.homeArticle.collectAsState()

    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        LazyColumn(
            state = listState, modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(items = article.article, key = { a, b -> b.name }) { a, b ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (a != 0) {
                        Divider()
                    }
                    HomeArticleItem(modifier = Modifier.fillMaxWidth(), b)
                }
            }
        }
    }
}

@Composable
fun Divider(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(1.px)
            .drawBackground(R.color.app_primary.inColor())
    )
}

@Composable
fun HomeArticleItem(modifier: Modifier, item: HomeArticle) {
    Column(modifier = modifier.padding(20.dp)) {

        val author by remember {
            derivedStateOf {
                if (item.author.isNotEmpty()) {
                    R.string.prefix_author.getString(item.author)
                } else if (item.share.isNotEmpty()) {
                    R.string.prefix_share.getString(item.share)
                } else {
                    R.string.prefix_author.getString(R.string.no_name.getString())
                }
            }
        }

        AuthorTitle(author)
        ItemTitle(text = item.name, modifier = Modifier.padding(top = 16.dp))
        Row(modifier = Modifier.padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
            ClassifyRow(item.superChapter, item.chapter)
            DescTitle(item.time)
        }
    }
}

@Composable
fun ClassifyRow(superChapter: String, chapter: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        DescTitle(R.string.prefix_classify.inString())
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp), verticalAlignment = Alignment.CenterVertically) {
            DescTitle(superChapter)
            Spacer(
                modifier = Modifier
                    .width(1.px)
                    .height(R.dimen.text_mini.inDp())
                    .drawBackground(R.color.app_primary.inColor())
            )
            DescTitle(chapter)
        }
    }
}