package com.wanandroid.app.tool

/**
 * 重新装载该数组，只有当[data]不为null时，会重新装载
 *
 * @param T
 * @param data
 */
fun <T, C : MutableCollection<in T>> C.reload(data: Iterable<T>?) = this.also {
    data?.let { data ->
        it.clear()
        it.addAll(data)
    }
}

/**
 * 重新装载该数组，只有当[data]不为null时，会重新装载
 *
 * @param T
 * @param data
 * @return
 */
fun <T, C : MutableCollection<in T>> C.reload(data: Sequence<T>?) = this.also {
    data?.let { data ->
        it.clear()
        it.addAll(data)
    }
}

/**
 * 重新装载该数组，只有当[data]不为null时，会重新装载
 *
 * @param T
 * @param data
 * @return
 */
fun <T, C : MutableCollection<in T>> C.reload(data: Array<out T>?) = this.also {
    data?.let { data ->
        it.clear()
        it.addAll(data)
    }
}


/**
 * 重新装载该数组，只有该数据不为null，才会重新装载，该结果仅添加不为null的数据，如果[data]是一个空的数组，依然会被清空
 *
 * @param T
 * @param data
 */
fun <T, C : MutableCollection<in T>> C.reloadNotNull(data: Iterable<T>?) = this.also {
    data?.let { input ->
        it.clear()
        it.addAll(input.filterNotNull())
    }
}

/**
 * 重新装载该数组，只有该数据不为null，才会重新装载，该结果仅添加不为null的数据，如果[data]是一个空的数组，依然会被清空
 *
 * @param T
 * @param data
 */
fun <T, C : MutableCollection<in T>> C.reloadNotNull(data: Sequence<T>?) = this.also {
    data?.let { input ->
        it.clear()
        val real = input.filterNotNull()
        for (item in real) {
            add(item)
        }
    }
}

/**
 * 重新装载该数组，只有该数据不为null，才会重新装载，该结果仅添加不为null的数据，如果[data]是一个空的数组，依然会被清空
 *
 * @param T
 * @param data
 */
fun <T, C : MutableCollection<in T>> C.reloadNotNull(data: Array<out T>?) = this.also {
    data?.let { input ->
        it.clear()
        it.addAll(input.filterNotNull())
    }
}

/**
 * 添加非空的数据
 *
 * @param T
 * @param data 可能为null的数据
 * @return
 */
fun <T, C : MutableCollection<in T>> C.addNotNull(data: T?) = this.also { collection -> data?.let { collection.add(it) } }


/**
 * 添加非空的元素
 *
 * @param T
 * @param elements
 * @return
 */
fun <T, C : MutableCollection<in T>> C.addAllNotNull(elements: Iterable<T>?) = this.also {
    elements?.let { input ->
        it.addAll(input.filterNotNull())
    }
}

/**
 * 添加非空的元素
 *
 * @param T
 * @param elements
 * @return
 */
fun <T, C : MutableCollection<in T>> C.addAllNotNull(elements: Sequence<T>?) = this.also {
    elements?.let { input ->
        it.addAll(input.filterNotNull())
    }
}

/**
 * 添加非空的元素
 *
 * @param T
 * @param elements
 * @return
 */
fun <T, C : MutableCollection<in T>> C.addAllNotNull(elements: Array<out T>?) = this.also {
    elements?.let { input ->
        it.addAll(input.filterNotNull())
    }
}

/**
 * 安全Slice列表
 *
 * @param T
 * @param indices
 * @return
 */
fun <T> List<T>.safeSlice(indices: IntRange): List<T> {

    if (this.isEmpty()) {
        return listOf()
    }

    var lastSliceIndex = indices.last

    if (indices.first > lastIndex) {
        return listOf()
    }

    if (indices.last > lastIndex) {
        lastSliceIndex = lastIndex
    }

    return this.slice(indices.first..lastSliceIndex)
}