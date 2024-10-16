package com.wandroid.app.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import com.wandroid.app.R
import com.wandroid.app.util.inColor
import com.wandroid.app.util.inPainter

@Composable
fun Icon(
    resource: Int,
    modifier: Modifier = Modifier,
    tintColor:Color = R.color.icon_active.inColor(),
    contentDescription: String? = null,
) {
    Image(
        painter = resource.inPainter(),
        modifier = modifier,
        contentDescription = contentDescription,
        colorFilter = ColorFilter.tint(tintColor)
    )
}