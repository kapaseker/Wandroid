package com.wandroid.app.page.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.wandroid.app.R
import com.wandroid.app.ext.navigation.BaseEmptyArgNavPath
import com.wandroid.app.page.home.business.HomeArticle
import com.wandroid.app.page.home.business.HomeViewModel
import com.wandroid.app.ui.widget.AuthorTitle
import com.wandroid.app.ui.widget.BottomBar
import com.wandroid.app.ui.widget.DescTitle
import com.wandroid.app.ui.widget.Icon
import com.wandroid.app.ui.widget.ItemTitle
import com.wandroid.app.ui.widget.MainText
import com.wandroid.app.ui.widget.simpleClick
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePage(
    controller: NavHostController,
    entry: NavBackStackEntry,
    i: HomeViewModel = viewModel(HomeViewModel::class),
    scope: CoroutineScope = rememberCoroutineScope(),
) {

    val article by i.homeArticle.collectAsState()

    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        LazyColumn(
            state = listState, modifier = Modifier
                .fillMaxSize()
                .drawBackground(R.color.background.inColor())
        ) {

            item {
                HomeHeader()
            }

            stickyHeader {
                Box(modifier = Modifier.fillMaxWidth()) {
                    HomeTaber()
                    Divider(Modifier.align(Alignment.BottomStart))
                }
            }

            itemsIndexed(items = article.article, key = { a, b -> b.name }) { a, b ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (a != 0) {
                        Divider()
                    }
                    HomeArticleItem(modifier = Modifier.fillMaxWidth(), b)
                }
            }
        }

        BottomBar(modifier = Modifier.align(Alignment.BottomCenter), select = 0)
    }
}

@Composable
private fun TabItem(s:Boolean, text:String) {

    val selectedColor = R.color.text_main.inColor()
    val activeColor = R.color.text_active.inColor()
    val selected by rememberUpdatedState(s)

    val color by remember {
        derivedStateOf {
            if (selected) selectedColor else activeColor
        }
    }

    Box(
        modifier = Modifier.size(R.dimen.home_tab.inDp())
    ) {
       MainText(text = text, color = color, size = R.dimen.text_mini.inDp(), modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun HomeTaber(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(R.dimen.home_tab.inDp())
            .drawBackground(R.color.background.inColor())
            .padding(horizontal = R.dimen.page_padding.inDp())
    ) {
        Row {
            TabItem(s = true, text = R.string.latest.inString())
            TabItem(s = false, text = R.string.hottest.inString())
        }
        Spacer(modifier = Modifier
            .width(R.dimen.home_tab.inDp())
            .height(2.px)
            .drawBackground(R.color.text_main.inColor())
            .align(Alignment.BottomStart))
    }
}

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(R.color.background.inColor())
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = R.dimen.page_padding.inDp()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MainText(text = R.string.home_page.inString(), size = R.dimen.text_page.inDp(), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .simpleClick { }
                    .padding(12.dp)
                    .size(20.dp),
                resource = R.drawable.search,
                contentDescription = R.string.search.inString()
            )
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