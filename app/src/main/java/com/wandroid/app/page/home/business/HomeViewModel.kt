package com.wandroid.app.page.home.business

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.wanandroid.app.repo.wan_anroid.response.Article
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeArticle(
    val name:String,
    val link:String,
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

class HomeViewModel : ViewModel() {

    private val _homeArticle = MutableStateFlow(HomeArticleState.empty())
    val homeArticle: StateFlow<HomeArticleState> = _homeArticle.asStateFlow()

    init {

    }
}