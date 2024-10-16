package com.wanandroid.app.tool

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * 创建EventFlow <br/>
 * EventFlow有这样的特性，仅对添加监听器时候的变化有效
 * @param T 泛型类型
 * @return EventFlow
 */
@Suppress("FunctionName")
fun <T> EventFlow() =
    MutableSharedFlow<T>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

/**
 * 创建一个SyncFlow <br/>
 * 该Flow的工作原理与LiveData一模一样，新添加的监听器会得到通知
 * @param T  泛型类型
 * @return 创建一个SyncFlow
 */
@Suppress("FunctionName")
fun <T> SyncFlow() =
    MutableSharedFlow<T>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

/**
 * 在ViewModel中创建协程
 *
 * @param context 上下文
 * @param start 启动方式
 * @param block 协程
 * @return 协程句柄
 */
fun ViewModel.launchInViewModel(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = this.viewModelScope.launch(context, start, block)