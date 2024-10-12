package com.wandroid.app.ext

import kotlin.math.PI

inline val Float.degree: Float get() = (this / 180f * PI).toFloat()