package com.wandroid.app.page.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.wandroid.app.R
import com.wandroid.app.util.navigation.BaseEmptyArgNavPath
import com.wandroid.app.page.home.business.HomeArticle
import com.wandroid.app.page.home.business.HomeViewModel
import com.wandroid.app.ui.widget.AuthorTitle
import com.wandroid.app.ui.widget.BottomBar
import com.wandroid.app.ui.widget.DescTitle
import com.wandroid.app.ui.widget.Icon
import com.wandroid.app.ui.widget.ItemTitle
import com.wandroid.app.ui.widget.MainText
import com.wandroid.app.ui.widget.TextTaber
import com.wandroid.app.ui.widget.simpleClick
import com.wandroid.app.ui.widget.drawBackground
import com.wandroid.app.util.getString
import com.wandroid.app.util.inColor
import com.wandroid.app.util.inDp
import com.wandroid.app.util.inString
import com.wandroid.app.util.navigation.goto
import com.wandroid.app.util.navigation.gotoWebActivity
import com.wandroid.app.util.px
import kotlinx.collections.immutable.persistentListOf
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

    val context = LocalContext.current

    fun openWebAction(url:String) {
        context.gotoWebActivity(url)
//        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//        context.goto(browserIntent)
    }

    val article by i.homeArticle.collectAsState()

    var homeTab by rememberSaveable("HomeTab") { mutableIntStateOf(0) }
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .drawBackground(R.color.background.inColor())
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        LazyColumn(
            state = listState, modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = R.dimen.bottom_bar_height.inDp())
        ) {

            item {
                HomeHeader() {

                }
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
                    ArticleItem(modifier = Modifier.fillMaxWidth(), b) {
                        openWebAction(b.link)
                    }
                }
            }
        }

        BottomBar(modifier = Modifier.align(Alignment.BottomCenter), selected = homeTab) {
            homeTab = it
        }
    }
}

@Composable
private fun HomeTaber(modifier: Modifier = Modifier) {
    var selectTab by rememberSaveable { mutableIntStateOf(0) }
    TextTaber(
        modifier = modifier,
        tabs = persistentListOf(R.string.latest.inString(), R.string.hottest.inString()),
        selected = selectTab
    ) {
        selectTab = it
    }
}

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    onSearch:() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(R.color.background.inColor())
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = R.dimen.page_padding.inDp()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MainText(text = R.string.home_page.inString(),
                size = R.dimen.text_page.inDp(),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .simpleClick(onClick = onSearch)
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
            .drawBackground(R.color.app_primary_divider.inColor())
    )
}

@Composable
fun ArticleItem(modifier: Modifier, item: HomeArticle, onClick:() -> Unit) {
    Column(modifier = modifier
        .simpleClick(onClick = onClick)
        .padding(20.dp)) {

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