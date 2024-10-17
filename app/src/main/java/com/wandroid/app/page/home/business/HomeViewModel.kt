package com.wandroid.app.page.home.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanandroid.app.repo.wan_anroid.WanRepo
import com.wanandroid.app.tool.LoadData
import com.wanandroid.app.tool.PageData
import com.wandroid.app.page.HOME_ARTICLE_ROWS
import com.wandroid.app.util.request
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeArticle(
    val id: Long,
    val name: String,
    val link: String,
    val share: String,
    val author: String,
    val superChapterId: Long,
    val superChapter: String,
    val chapterId: Long,
    val chapter: String,
    val time: String,
    val tags: List<String>
)


data class HomeArticleState(
    val pageData: PageData<Unit>,
    val article: ImmutableList<HomeArticle>
) {
    companion object {
        private val EMPTY = HomeArticleState(PageData.loading(), persistentListOf())

        @JvmStatic
        fun empty() = EMPTY
    }
}

sealed interface HomeIntent

data object InitArticle : HomeIntent

data object RefreshArticle : HomeIntent

class HomeViewModel : ViewModel() {

    private val _homeArticle = MutableStateFlow(HomeArticleState.empty())
    val homeArticle: StateFlow<HomeArticleState> = _homeArticle.asStateFlow()

    init {
        this want InitArticle
    }

    infix fun want(intent: HomeIntent) {
        when (intent) {
            RefreshArticle -> {

            }

            InitArticle -> {
                loadArticles()
            }
        }
    }

    private fun loadArticles() {
        _homeArticle.update { HomeArticleState.empty() }
        viewModelScope.launch(Dispatchers.IO) {
            val page = requestArticle()
            _homeArticle.update { HomeArticleState(page.onlyState(), page.data.toImmutableList()) }
        }
    }

    private suspend fun requestArticle(): PageData<HomeArticle> {
        return request(HOME_ARTICLE_ROWS, WanRepo.service().article(0, HOME_ARTICLE_ROWS)) { dt ->
            dt?.data?.datas?.toList().orEmpty().map {
                HomeArticle(id = it.id,
                    name = it.title,
                    link = it.link,
                    share = it.shareUser,
                    author = it.author,
                    superChapterId = it.superChapterId,
                    superChapter = it.superChapterName,
                    chapterId = it.chapterId,
                    chapter = it.chapterName,
                    time = it.niceDate,
                    tags = it.tags.map { tag -> tag.name })
            }
        }
    }
}