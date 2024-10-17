package com.wanandroid.app.tool

import android.provider.ContactsContract.Data

data class LoadData<T>(
    val loadingState: LoadingState,
    val resultState: ResultState,
    val dataState: DataState,
    val data: T,
) {

    companion object {

        /**
         * 数据加载中
         *
         * @param T
         * @param def
         */
        fun <T> loading(def: T) = LoadData(
            LoadingState.START,
            ResultState.SUCCESS,
            DataState.EMPTY,
            def
        )

        /**
         * 完全加载
         *
         * @param T
         * @param data
         */
        fun <T> complete(data: T) = LoadData(
            LoadingState.COMPLETE,
            ResultState.SUCCESS,
            DataState.COMPLETE,
            data,
        )

        /**
         * 没有更多的情况，部分加载完成
         *
         * @param T
         * @param data
         */
        fun <T> nomore(data: T) = LoadData(
            LoadingState.COMPLETE,
            ResultState.SUCCESS,
            DataState.PARTLY_COMPLETE,
            data,
        )

        /**
         * 空结果，无结果
         *
         * @param T
         */
        fun <T> empty(def: T) = LoadData(
            LoadingState.COMPLETE,
            ResultState.SUCCESS,
            DataState.EMPTY,
            def,
        )

        /**
         * 数据加载错误
         *
         * @param T
         * @param error
         * @param def
         */
        fun <T> error(error: ResultState, def: T): LoadData<T> {

            if (error == ResultState.SUCCESS) {
                throw  IllegalArgumentException("error can't be successful state")
            }

            return LoadData(
                loadingState = LoadingState.COMPLETE,
                resultState = error,
                dataState = DataState.EMPTY,
                data = def
            )
        }
    }

    /**
     * 数据转换
     *
     * @param D
     * @param transform
     * @receiver
     * @return
     */
    fun <D> map(transform: (T) -> D): LoadData<D> {
        return LoadData(
            loadingState = this.loadingState,
            resultState = this.resultState,
            dataState = this.dataState,
            data = transform.invoke(this.data)
        )
    }

    /**
     * 只要状态，不要数据
     *
     * @return
     */
    fun onlyState(): LoadData<Unit> {
        return LoadData(
            loadingState = this.loadingState,
            resultState = this.resultState,
            dataState = this.dataState,
            data = Unit,
        )
    }
}

data class PageData<T>(
    val loadingState: LoadingState,
    val resultState: ResultState,
    val dataState: DataState,
    val data: List<T>,
) {

    companion object {

        /**
         * 数据加载中
         *
         * @param T
         * @param def
         */
        fun <T> loading() = PageData<T>(
            LoadingState.START,
            ResultState.SUCCESS,
            DataState.EMPTY,
            emptyList(),
        )

        /**
         * 完全加载
         *
         * @param T
         * @param data
         */
        fun <T> complete(data: List<T>) = PageData(
            LoadingState.COMPLETE,
            ResultState.SUCCESS,
            DataState.COMPLETE,
            data,
        )

        /**
         * 没有更多的情况，部分加载完成
         *
         * @param T
         * @param data
         */
        fun <T> nomore(data: List<T>) = PageData(
            LoadingState.COMPLETE,
            ResultState.SUCCESS,
            DataState.PARTLY_COMPLETE,
            data,
        )

        /**
         * 空结果，无结果
         *
         * @param T
         */
        fun <T> empty(def: List<T>) = PageData(
            LoadingState.COMPLETE,
            ResultState.SUCCESS,
            DataState.EMPTY,
            def,
        )

        /**
         * 自动结果
         *
         * @param expectedSize 期望的数量
         * @param def
         */
        fun <T> auto(expectedSize: Int, def: List<T>) = PageData(
            LoadingState.COMPLETE,
            ResultState.SUCCESS,
            when {
                def.isEmpty() -> DataState.EMPTY
                def.size >= expectedSize -> DataState.PARTLY_COMPLETE
                else -> DataState.COMPLETE
            },
            def,
        )

        /**
         * 数据加载错误
         *
         * @param T
         * @param error
         */
        fun <T> error(error: ResultState): PageData<T> {

            if (error == ResultState.SUCCESS) {
                throw  IllegalArgumentException("error can't be successful state")
            }

            return PageData(
                loadingState = LoadingState.COMPLETE,
                resultState = error,
                dataState = DataState.EMPTY,
                data = emptyList()
            )
        }
    }

    /**
     * 数据转换
     *
     * @param D
     * @param transform
     * @receiver
     * @return
     */
    fun <D> map(transform: (T) -> D): PageData<D> {
        return PageData(
            this.loadingState,
            this.resultState,
            this.dataState,
            this.data.map { transform(it) },
        )
    }

    /**
     * 数据转换
     *
     * @param D
     * @param result
     * @return
     */
    fun <D> map(result: List<D>): PageData<D> {
        return PageData(
            this.loadingState,
            this.resultState,
            this.dataState,
            result
        )
    }

    /**
     * 只要状态，不要数据
     *
     * @return
     */
    fun onlyState(): PageData<Unit> {
        return PageData(
            loadingState = this.loadingState,
            resultState = this.resultState,
            dataState = this.dataState,
            data = emptyList(),
        )
    }
}

/**
 * 结果状态
 *
 */
enum class ResultState {

    /**
     * 成功
     *
     */
    SUCCESS,

    /**
     * 未知错误
     *
     */
    UNKNOWN_ERROR,

    /**
     * 服务器错误
     *
     */
    SERVER_ERROR,

    /**
     * 网络错误
     */
    NET_ERROR,
}

/**
 * 加载状态，开始，结束
 */
enum class LoadingState {
    /**
     * 开始，
     *
     */
    START,

    /**
     * 结束
     *
     */
    COMPLETE,
}

enum class DataState {
    /**
     * 完整加载
     *
     */
    COMPLETE,

    /**
     * 没有完整的加载，只是加载了一部分
     *
     */
    PARTLY_COMPLETE,

    /**
     * 空的
     *
     */
    EMPTY,
}