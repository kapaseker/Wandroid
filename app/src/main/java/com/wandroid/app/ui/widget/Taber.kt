package com.wandroid.app.ui.widget

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.wandroid.app.R
import com.wandroid.app.util.inColor
import com.wandroid.app.util.inDp
import com.wandroid.app.util.toPxInt
import kotlinx.collections.immutable.ImmutableList

@Composable
fun TextTaber(modifier: Modifier = Modifier, tabs: ImmutableList<String>, selected: Int, onSelect: (Int) -> Unit) {

    val tabsState by rememberUpdatedState(tabs)
    val selectedIndexState by rememberUpdatedState(selected)
    val onSelectState by rememberUpdatedState(onSelect)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(R.dimen.home_tab.inDp())
            .drawBackground(R.color.background.inColor())
            .padding(horizontal = R.dimen.page_padding.inDp())
    ) {
        Row {
            for ((i, e) in tabsState.withIndex()) {
                TabItem(s = i == selectedIndexState, text = e) {
                    onSelectState.invoke(i)
                }
            }
        }

        val offsetDp by animateIntOffsetAsState(targetValue = IntOffset(R.dimen.home_tab_width.inDp().toPxInt * selected, 0), label = "Tab")

        Spacer(modifier = Modifier
            .width(R.dimen.home_tab_width.inDp())
            .height(2.dp)
            .offset {
                offsetDp
            }
            .drawBackground(R.color.text_main.inColor())
            .align(Alignment.BottomStart)
        )
    }
}

@Composable
private fun TabItem(s:Boolean, text:String, onClick:() -> Unit) {

    val selectedColor = R.color.text_main.inColor()
    val activeColor = R.color.text_active.inColor()
    val selected by rememberUpdatedState(s)

    val color by remember {
        derivedStateOf {
            if (selected) selectedColor else activeColor
        }
    }

    Box(
        modifier = Modifier.size(width = R.dimen.home_tab_width.inDp(), height = R.dimen.home_tab_height.inDp(),)
            .simpleClick(onClick = onClick)
    ) {
       MainText(
           text = text,
           color = color,
           size = R.dimen.text_mini.inDp(),
           modifier = Modifier.align(Alignment.Center)
       )
    }
}