package com.wanandroid.app.tool

/**
 * 页面结果
 *
 * @author PG.Xie
 * created on 2021/10/11
 */
data class PageResult<T>(val data: List<T>, val state: PageState) {

    companion object {
        /**
         * 完全加载
         *
         * @param T
         * @param data
         */
        fun <T> complete(data: List<T>) = PageResult(data, PageState.COMPLETE)

        /**
         * 没有更多的情况，部分加载完成
         *
         * @param T
         * @param data
         */
        fun <T> nomore(data: List<T>) = PageResult(data, PageState.PARTLY_COMPLETE)

        /**
         * 空结果，无结果
         *
         * @param T
         */
        fun <T> empty() = PageResult(emptyList<T>(), PageState.EMPTY)

        /**
         * 自动，当 [datas] 的大小：
         * 1. 为0，则表示空状态[empty]
         * 2. 小于[expectedSize] 表示没有更多
         * 3. 其他状态为成功，完全加载
         *
         * @param T
         * @param datas
         * @param expectedSize
         */
        fun <T> auto(data: List<T>, expectedSize: Int) = when {
            data.isEmpty() -> empty()
            data.size >= expectedSize -> complete(data)
            else -> nomore(data)
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
    fun <D> map(transform: (T) -> D): PageResult<D> {
        return PageResult(this.data.map(transform), this.state)
    }

    /**
     * 数据转换
     *
     * @param D
     * @param data
     * @receiver
     * @return
     */
    fun <D> map(data: List<D>): PageResult<D> {
        return PageResult(data, this.state)
    }
}

data class LoadResult<T>(val data: T, val state: PageState) {
    companion object {

        /**
         * 完全加载
         *
         * @param T
         * @param data
         */
        fun <T> complete(data: T) = LoadResult(data, PageState.COMPLETE)

        /**
         * 没有更多的情况，部分加载完成
         *
         * @param T
         * @param data
         */
        fun <T> nomore(data: T) = LoadResult(data, PageState.PARTLY_COMPLETE)

        /**
         * 空结果，无结果
         *
         * @param T
         */
        fun <T> empty(def: T) = LoadResult(def, PageState.EMPTY)
    }

    /**
     * 数据转换
     *
     * @param D
     * @param transform
     * @receiver
     * @return
     */
    fun <D> map(transform: (T) -> D): LoadResult<D> {
        return LoadResult(transform.invoke(this.data), this.state)
    }
}

data class LoadData<T>(
    val loadingState: LoadingState,
    val resultState: ResultState,
    val result: LoadResult<T>
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
            LoadResult.empty(def)
        )

        /**
         * 数据成功加载
         *
         * @param T
         * @param result
         */
        fun <T> success(result: LoadResult<T>) = LoadData(
            LoadingState.COMPLETE,
            ResultState.SUCCESS,
            result,
        )

        /**
         * 数据加载错误
         *
         * @param T
         * @param error
         * @param def
         */
        fun <T> error(error: ResultState, def: T) = LoadData(
            LoadingState.COMPLETE,
            if (error == ResultState.SUCCESS) {
                ResultState.UNKNOWN_ERROR
            } else {
                error
            },
            LoadResult.empty(def)
        )
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
        return LoadData(this.loadingState, this.resultState, this.result.map(transform))
    }
}

data class PageData<T>(
    val loadingState: LoadingState,
    val resultState: ResultState,
    val result: PageResult<T> = PageResult.empty()
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
            PageResult.empty()
        )

        /**
         * 数据成功加载
         *
         * @param T
         * @param result
         */
        fun <T> success(result: PageResult<T>) = PageData<T>(
            LoadingState.COMPLETE,
            ResultState.SUCCESS,
            result,
        )

        /**
         * 数据加载错误
         *
         * @param T
         * @param error
         */
        fun <T> error(error: ResultState) = PageData<T>(
            LoadingState.COMPLETE,
            if (error == ResultState.SUCCESS) {
                ResultState.UNKNOWN_ERROR
            } else {
                error
            },
            PageResult.empty()
        )
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
            this.result.map(transform)
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
            this.result.map(result)
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

enum class PageState {
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