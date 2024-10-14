package com.wandroid.app.util

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.blankj.utilcode.util.Utils

@Composable
fun @receiver:StringRes Int.inString():String {
    return stringResource(this)
}

@Composable
fun @receiver:StringRes Int.inString(vararg formatArgs: Any):String {
    return stringResource(this, *formatArgs)
}

@Composable
fun @receiver:StringRes Int.inStringArray():Array<String> {
    return stringArrayResource(this)
}

fun @receiver:StringRes Int.getString():String {
    return Utils.getApp().resources.getString(this)
}

fun @receiver:StringRes Int.getString(vararg formatArgs: Any):String {
    return Utils.getApp().resources.getString(this, *formatArgs)
}

fun @receiver:StringRes Int.getStringArray():Array<String> {
    return Utils.getApp().resources.getStringArray(this)
}

@Composable
@ReadOnlyComposable
fun @receiver:DimenRes Int.inDp(): Dp {
    return dimensionResource(id = this)
}

@Composable
fun @receiver:DimenRes Int.inPx(): Float {
    return dimensionResource(id = this).toPx
}

@Composable
fun @receiver:DimenRes Int.inSp(): TextUnit {
    return dimensionResource(id = this).toSp
}

@Composable
@ReadOnlyComposable
fun @receiver:ColorRes Int.inColor(): Color {
    return colorResource(id = this)
}

@Composable
fun @receiver:ColorRes Int.colorInBrush(): Brush {
    return SolidColor(colorResource(id = this))
}

@Composable
fun @receiver:DrawableRes Int.inPainter(): Painter {
    return painterResource(id = this)
}

@Composable
fun @receiver:DrawableRes Int.drawableInBrush(): Brush {
    return ShaderBrush(ImageShader(ImageBitmap.imageResource(this)))
}


val Dp.toPxInt :Int @Composable get() =  with(LocalDensity.current) { this@toPxInt.toPx().toInt() }
val Dp.toPx :Float @Composable get() =  with(LocalDensity.current) { this@toPx.toPx() }
val Dp.toSp :TextUnit @Composable get() =  with(LocalDensity.current) { this@toSp.toSp() }
val Int.px :Dp @Composable get() =  with(LocalDensity.current) { this@px.toDp() }