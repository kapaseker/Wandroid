package com.wandroid.app.ui.widget

import android.view.SoundEffectConstants
import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalView
import com.wandroid.app.ui.PRESS_ALPHA

/**
 * 日志标签常量，用于日志输出标识
 */
private const val TAG = "Click"

/**
 * 点击事件的响应间隔时间（毫秒），用于防止快速连续点击
 */
private const val CLICK_TIMER = 400

/**
 * click with alpha change
 *
 * @param clickable
 * @param soundPlay
 * @param onClick
 * @receiver
 * @return
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.simpleClick(
    clickable: Boolean = true,
    soundPlay: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = LocalIndication.current,
    onLongClick: (() -> Unit)? = null,
    onClick: () -> Unit,
): Modifier {

    val onClickState by rememberUpdatedState(newValue = onClick)

    val view = LocalView.current

    return this
        .combinedClickable(
            interactionSource = interactionSource,
            indication = indication,
            enabled = clickable,
            onLongClick = onLongClick,
            onClick = {
                // 如果设置了播放声音，执行播放声音效果
                if (soundPlay) {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                }
                // 调用传入的onClick函数执行真正的点击逻辑
                onClickState.invoke()
            }
        )
}

@Composable
fun clickSound() {
    LocalView.current.playSoundEffect(SoundEffectConstants.CLICK)
}

fun View.clickSound() {
    playSoundEffect(SoundEffectConstants.CLICK)
}

/**
 * 悄悄的可点击，主要是为了拦截点击事件，没有透明度变化，没有声音
 *
 * @param onClick
 * @receiver
 * @return
 */
@Composable
fun Modifier.muteClick(
    onClick: () -> Unit,
): Modifier {
    return justClick(clickable = true, soundPlay = false, onClick = onClick)
}

/**
 * just click, no alpha change
 *
 * @param clickable
 * @param soundPlay
 * @param onClick
 * @receiver
 * @return
 */
@Composable
fun Modifier.justClick(
    clickable: Boolean = true,
    soundPlay: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
): Modifier {
    return this.simpleClick(clickable = clickable, soundPlay = soundPlay, interactionSource = interactionSource, indication = null, onLongClick = null, onClick = onClick)
}