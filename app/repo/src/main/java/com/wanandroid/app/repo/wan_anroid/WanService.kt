package com.wanandroid.app.repo.wan_anroid

import com.wanandroid.app.repo.wan_anroid.response.ArticleResult
import com.wanandroid.app.repo.wan_anroid.response.WendaResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IWanService {
    @GET("/popular/wenda/json")
    fun wenda(): Call<WendaResult>

    /**
     * Article
     *
     * @param page page, start by 0.
     */
    @GET("/article/list/{page}/json")
    fun article(@Path("page") page: Int, @Query("page_size") row:Int): Call<ArticleResult>
}