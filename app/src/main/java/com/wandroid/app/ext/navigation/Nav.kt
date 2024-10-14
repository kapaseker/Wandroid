package com.wandroid.app.ext.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument

interface INavArg

object EmptyArg : INavArg

interface INavPath<ARG : INavArg> {
    val path: String

    fun arguments(): List<NamedNavArgument>

    fun make(arg: ARG): String

    fun unpack(savedState: SavedStateHandle): ARG

    /**
     * check if route is matching
     *
     * @param route 当前的route值
     * @return true表示相等，false表示不相等
     */
    fun matchRoute(route: String?): Boolean {
        if (!route.isNullOrEmpty()) {
            var pathIndex = this.path.indexOf('/')
            if (pathIndex < 0) {
                pathIndex = this.path.indexOf('?')
            }
            if (pathIndex < 0) {
                pathIndex = this.path.length
            }
            return route == this.path.substring(0, pathIndex)
        }
        return false
    }
}

abstract class BaseEmptyArgNavPath : INavPath<EmptyArg> {
    override fun arguments(): List<NamedNavArgument> = emptyList()
    override fun make(arg: EmptyArg): String = this.path
    override fun unpack(savedState: SavedStateHandle): EmptyArg = EmptyArg
}