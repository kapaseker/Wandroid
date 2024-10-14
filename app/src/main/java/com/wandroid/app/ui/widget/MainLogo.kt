package com.wandroid.app.ui.widget

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wandroid.app.R
import com.wandroid.app.ext.degree
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.time.Duration.Companion.milliseconds


@Preview
@Composable
fun PreviewMainLogo() {
    MainLogo(modifier = Modifier.size(200.dp))
}

private const val SINGLE_ANIMATE_TIME = 200


/**
 * Logo的动画接口
 */
sealed interface ILogoAnimation;

/**
 * 闲逛
 */
data object Hanging : ILogoAnimation;

@Composable
fun MainLogo(
    modifier: Modifier,
    logoAnimation: ILogoAnimation? = null,
) {

    val color = colorResource(R.color.android)

    val eyeAnimate = remember { Animatable(0f) }
    val earAnimate = remember { Animatable(0f) }

    LaunchedEffect(logoAnimation) {
        if (logoAnimation == null) {
            return@LaunchedEffect
        }

        when (logoAnimation) {
            is Hanging -> {
                while (true) {
                    repeat(2) {
                        eyeAnimate.animateTo(1f, tween(durationMillis = SINGLE_ANIMATE_TIME))
                        eyeAnimate.animateTo(0f, tween(durationMillis = SINGLE_ANIMATE_TIME))
                    }
                    delay((SINGLE_ANIMATE_TIME * 2).milliseconds)
                    repeat(2) {
                        earAnimate.animateTo(1f, tween(durationMillis = SINGLE_ANIMATE_TIME))
                        earAnimate.animateTo(0f, tween(durationMillis = SINGLE_ANIMATE_TIME))
                    }
                    delay((SINGLE_ANIMATE_TIME * 2).milliseconds)
                }
            }
        }
    }

    Spacer(modifier = modifier.drawWithCache {
        onDrawBehind {

            val square = min(size.width, size.height)
            val radius = square / 2

            clipRect {
                val bodySize = square// * 0.75f
                val earLength = radius * 0.64f * 2
                val earHorizontal = earLength * cos(60f.degree - earAnimate.value * (15f.degree))
                val earTop = size.height - earLength * sin(60f.degree - earAnimate.value * (15f.degree))
                val earWidth = 0.038f * size.width
                drawLine(color, start = Offset(square / 2, size.height), end = Offset(radius + earHorizontal, earTop), strokeWidth = earWidth, cap = StrokeCap.Round)
                drawLine(color, start = Offset(square / 2, size.height), end = Offset(radius - earHorizontal, earTop), strokeWidth = earWidth, cap = StrokeCap.Round)
                val bodyOffset = size.height * 0.04f
                drawArc(
                    color = color,
                    startAngle = 0f,
                    sweepAngle = -180f,
                    useCenter = false,
                    size = Size(bodySize, bodySize),
                    topLeft = Offset(0f, size.height / 2 + bodyOffset)
                )
            }

            val eyeRadius = 0.042f * size.width
            val eyeTop = size.height * 0.06f + size.height * 0.75f
            val eyeMoveDistance = square * 0.1f * eyeAnimate.value
            //draw eye
            drawCircle(Color.White, eyeRadius, center = Offset(radius / 2 + eyeRadius / 2 + eyeMoveDistance, eyeTop))
            drawCircle(Color.White, eyeRadius, center = Offset(size.height * 0.75f - eyeRadius / 2 + eyeMoveDistance, eyeTop))
        }
    })
}