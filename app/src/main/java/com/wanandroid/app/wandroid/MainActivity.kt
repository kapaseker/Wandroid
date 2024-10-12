package com.wanandroid.app.wandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.wanandroid.app.wandroid.ui.theme.WandroidTheme
import kotlin.math.PI
import kotlin.math.min
import kotlin.math.tan

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WandroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android", modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

inline val Float.degree: Float get() = (this / 180f * PI).toFloat()

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )

    val color = colorResource(R.color.android)

    //Perfect Now
    Spacer(modifier = Modifier
        .size(100.dp)
//        .background(Color.Blue)
        .drawWithCache {
            onDrawBehind {
                clipRect {
                    val square = min(size.width, size.height)
                    val radius = square / 2
                    val region = radius * 0.64f
                    val bodySize = square// * 0.75f
                    val ear = square - region * tan(60f.degree)
                    val earWidth = 0.038f * size.width
                    drawLine(color, start = Offset(square / 2, size.height), end = Offset(radius + region, ear), strokeWidth = earWidth, cap = StrokeCap.Round)
                    drawLine(color, start = Offset(square / 2, size.height), end = Offset(radius * 0.36f, ear), strokeWidth = earWidth, cap = StrokeCap.Round)
                    val bodyOffset = size.height * 0.04f
                    drawArc(
                        color = color,
                        startAngle = 0f,
                        sweepAngle = -180f,
                        useCenter = false,
                        size = Size(bodySize, bodySize),
                        topLeft = Offset(0f, size.height / 2 + bodyOffset)
                    )
                    val eyeRadius = 0.042f * size.width
                    val eyeTop = size.height * 0.06f + size.height * 0.75f
                    drawCircle(Color.White, eyeRadius, center = Offset(radius / 2 + eyeRadius / 2, eyeTop))
                    drawCircle(Color.White, eyeRadius, center = Offset(size.height * 0.75f - eyeRadius / 2, eyeTop))
                }
//                drawCircle(color = Color.White)
            }
        })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WandroidTheme {
        Greeting("Android")
    }
}