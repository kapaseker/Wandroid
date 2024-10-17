package com.wandroid.app.util.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import com.wandroid.app.MainActivity
import com.wandroid.app.WebActivity

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


fun Context.gotoWebActivity(url: String) {
    goto(WebActivity.makeIntent(this, url))
}

fun Context.goto(intent: Intent) {
    startActivity(intent)
}

fun <T : Activity> Context.goto(activity: Class<T>) {
    goto(Intent(this, activity))
}
