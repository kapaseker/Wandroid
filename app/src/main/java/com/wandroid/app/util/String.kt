package com.wandroid.app.util

private const val EMPTY_STRING = ""
fun emptyString() = EMPTY_STRING
fun String?.orEmpty() = this ?: emptyString()