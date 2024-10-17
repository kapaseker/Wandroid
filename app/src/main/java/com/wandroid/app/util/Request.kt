package com.wandroid.app.util

import androidx.lifecycle.ViewModel
import com.wanandroid.app.tool.DataState
import com.wanandroid.app.tool.LoadData
import com.wanandroid.app.tool.LoadingState
import com.wanandroid.app.tool.PageData
import com.wanandroid.app.tool.ResultState
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import java.io.IOException
import kotlin.coroutines.resume

suspend fun <T> ViewModel.request(requester: Call<T>) = suspendCancellableCoroutine<LoadData<T?>> { continuation ->

    try {
        val response = requester.execute()
        if (response.isSuccessful) {
            continuation.resume(LoadData.complete(response.body()))
        } else {
            continuation.resume(LoadData.error(ResultState.SERVER_ERROR, null))
        }
    } catch (exception: IOException) {
        continuation.resume(LoadData.error(ResultState.NET_ERROR, null))
    }

    continuation.invokeOnCancellation {
        requester.cancel()
    }
}

suspend fun <T, D> ViewModel.request(expectedSize:Int, requester: Call<T>, mapper: (T?) -> List<D>) = suspendCancellableCoroutine<PageData<D>> { continuation ->

    try {
        val response = requester.execute()
        if (response.isSuccessful) {
            continuation.resume(PageData.auto(expectedSize, mapper.invoke(response.body())))
        } else {
            continuation.resume(PageData.error(ResultState.SERVER_ERROR))
        }
    } catch (exception: IOException) {
        continuation.resume(PageData.error(ResultState.NET_ERROR))
    }

    continuation.invokeOnCancellation {
        requester.cancel()
    }
}