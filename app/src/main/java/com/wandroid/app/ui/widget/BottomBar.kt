package com.wandroid.app.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wandroid.app.R
import com.wandroid.app.util.inColor
import com.wandroid.app.util.inPainter
import com.wandroid.app.util.px
import com.wandroid.app.util.toPx

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    select: Int,
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .blurShadow(offsetY = -2.dp.toPx, color = R.color.card_shadow.inColor(), blurRadius = 10.dp.toPx)
            .drawBackground(R.color.background.inColor()),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BottomBarIcon(modifier = Modifier.fillMaxHeight().aspectRatio(1f), s = true, a = R.drawable.house, b = R.drawable.house_fill)
//        BottomBarIcon(false, R.drawable.bar_chart, R.drawable.bar_chart_fill)
        BottomBarIcon(modifier = Modifier.fillMaxHeight().aspectRatio(1f), s = false, a = R.drawable.person, b = R.drawable.person_fill)
    }
}

/**
 * Bottom bar icon
 *
 * @param s 是否选中
 * @param a a面
 * @param b b面 ，选中面
 */
@Composable
private fun BottomBarIcon(
    modifier: Modifier = Modifier,
    s: Boolean,
    a: Int,
    b: Int,
) {
    val selected by rememberUpdatedState(s)

    val icon by remember {
        derivedStateOf {
            if (selected) {
                b
            } else {
                a
            }
        }
    }

    val activeColor = R.color.icon_active.inColor()
    val selectedColor = R.color.icon_select.inColor()

    val iconColor by remember {
        derivedStateOf {
            if (selected) {
                selectedColor
            } else {
                activeColor
            }
        }
    }

    Box(modifier = modifier
        .clip(CircleShape)
        .simpleClick { }
    ) {
        Icon(
            modifier = Modifier.size(24.dp).align(Alignment.Center),
            resource = icon,
            tintColor = iconColor,
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun PreviewBottomBar() {
    BottomBar(select = 0)
}