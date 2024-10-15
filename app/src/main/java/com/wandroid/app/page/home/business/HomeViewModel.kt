package com.wandroid.app.page.home.business

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanandroid.app.repo.wan_anroid.WanRepo
import com.wanandroid.app.repo.wan_anroid.response.Article
import com.wandroid.app.R
import com.wandroid.app.util.getString
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeArticle(
    val id:Long,
    val name: String,
    val link: String,
    val share:String,
    val author: String,
    val superChapterId:Long,
    val superChapter:String,
    val chapterId:Long,
    val chapter:String,
    val time:String,
    val tags: List<String>
)


data class HomeArticleState(
    val article: ImmutableList<HomeArticle>
) {
    companion object {
        private val EMPTY = HomeArticleState(persistentListOf())

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
        viewModelScope.launch(Dispatchers.IO) {
            requestArticle()
        }
    }

    private fun requestArticle() {
        val response = WanRepo.service().article(0, 20).execute()
        if (response.isSuccessful) {
            _homeArticle.tryEmit(HomeArticleState(response.body()?.let { body ->
                body.data.datas.map {

                    HomeArticle(
                        id = it.id,
                        name = it.title,
                        link = it.link,
                        share = it.shareUser,
                        author = it.author,
                        superChapterId = it.superChapterId,
                        superChapter = it.superChapterName,
                        chapterId = it.chapterId,
                        chapter = it.chapterName,
                        time = it.niceDate,
                        tags = it.tags.map { tag -> tag.name }
                    )
                }
            }.orEmpty().toImmutableList()))
        }
    }
}