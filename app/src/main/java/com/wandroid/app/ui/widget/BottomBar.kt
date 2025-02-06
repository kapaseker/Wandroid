package com.wandroid.app.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wandroid.app.R
import com.wandroid.app.ui.components.components.Icon
import com.wandroid.app.ui.components.components.NavigationBar
import com.wandroid.app.ui.components.components.NavigationBarItem
import com.wandroid.app.util.inColor
import com.wandroid.app.util.inDp
import com.wandroid.app.util.inPainter
import com.wandroid.app.util.toPx

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    selected: Int,
    onSelect:(Int) -> Unit,
) {

    val selectState by rememberUpdatedState(selected)
    val onSelectState by rememberUpdatedState(onSelect)

    NavigationBar(
        modifier = modifier
            .height(R.dimen.bottom_bar_height.inDp())
            .blurShadow(offsetY = -2.dp.toPx, color = R.color.card_shadow.inColor(), blurRadius = 10.dp.toPx)
            .drawBackground(R.color.background.inColor())
    ) {

        val homeSelected by remember {
            derivedStateOf {
                selectState == 0
            }
        }

        val homeIcon by remember {
            derivedStateOf {
                if (homeSelected) {
                    R.drawable.house_fill
                }else{
                    R.drawable.house
                }
            }
        }

        NavigationBarItem(
            icon = { Icon(modifier = Modifier.size(R.dimen.bottom_bar_icon_size.inDp()), painter = homeIcon.inPainter(), contentDescription = "Home") },
            selected = homeSelected,
            onClick = { onSelectState.invoke(0) }
        )

        val personSelected by remember {
            derivedStateOf {
                selectState == 1
            }
        }

        val personIcon by remember {
            derivedStateOf {
                if (personSelected) {
                    R.drawable.person_fill
                }else{
                    R.drawable.person
                }
            }
        }

        NavigationBarItem(
            icon = { Icon(modifier = Modifier.size(R.dimen.bottom_bar_icon_size.inDp()), painter = personIcon.inPainter(), contentDescription = "Person") },
            selected = personSelected,
            onClick = { onSelectState.invoke(1) }
        )
    }
}

///**
// * Bottom bar icon
// *
// * @param s 是否选中
// * @param a a面
// * @param b b面 ，选中面
// */
//@Composable
//private fun BottomBarIcon(
//    modifier: Modifier = Modifier,
//    s: Boolean,
//    a: Int,
//    b: Int,
//    onClick:() -> Unit,
//) {
//    val selected by rememberUpdatedState(s)
//
//    val icon by remember {
//        derivedStateOf {
//            if (selected) {
//                b
//            } else {
//                a
//            }
//        }
//    }
//
//    val activeColor = R.color.icon_active.inColor()
//    val selectedColor = R.color.icon_select.inColor()
//
//    val iconColor by remember {
//        derivedStateOf {
//            if (selected) {
//                selectedColor
//            } else {
//                activeColor
//            }
//        }
//    }
//
//    Box(modifier = modifier
//        .clip(CircleShape)
//        .simpleClick(onClick = onClick)
//    ) {
//        Icon(
//            modifier = Modifier.size(24.dp).align(Alignment.Center),
//            resource = icon,
//            tintColor = iconColor,
//            contentDescription = null,
//        )
//    }
//}

@Preview
@Composable
fun PreviewBottomBar() {
    BottomBar(selected = 0) { }
}