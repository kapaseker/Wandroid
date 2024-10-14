package com.wanandroid.app.repo.wan_anroid

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WanRepo {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(IWanService::class.java)

    fun service(): IWanService = service
}