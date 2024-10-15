package com.wandroid.app.ui.widget

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.wandroid.app.R
import com.wandroid.app.util.inColor
import com.wandroid.app.util.inDp
import com.wandroid.app.util.inString
import com.wandroid.app.util.toSp

@Preview
@Composable
fun MainText(
    modifier: Modifier = Modifier,
    text: String = R.string.app_name.inString(),
    size: Dp = R.dimen.text_main.inDp(),
    color: Color = R.color.text_main.inColor(),
    fontWeight: FontWeight = FontWeight.Normal,
    lineHeight: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    textAlign: TextAlign? = null,
    textStyle: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = size.toSp,
        fontWeight = fontWeight,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = textAlign,
        lineHeight = lineHeight,
        style = textStyle,
    )
}

@Preview
@Composable
fun SingleLineMainText(
    modifier: Modifier = Modifier,
    text: String = R.string.app_name.inString(),
    size: Dp = R.dimen.text_main.inDp(),
    color: Color = R.color.text_main.inColor(),
    fontWeight: FontWeight = FontWeight.Normal,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textAlign: TextAlign? = null,
) {
    MainText(
        modifier = modifier,
        text = text,
        size = size,
        color = color,
        fontWeight = fontWeight,
        maxLines = 1,
        overflow = overflow,
        textAlign = textAlign,
        textStyle = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
    )
}

@Composable
fun MainText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    size: Dp = R.dimen.text_main.inDp(),
    color: Color = R.color.text_main.inColor(),
    fontWeight: FontWeight = FontWeight.Normal,
    lineHeight: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    textAlign: TextAlign? = null,
    textStyle: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = size.toSp,
        color = color,
        fontWeight = fontWeight,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = textAlign,
        lineHeight = lineHeight,
        style = textStyle,
    )
}

@Composable
fun SingleLineMainText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    size: Dp = dimensionResource(id = R.dimen.text_main),
    color: Color = colorResource(id = R.color.text_main),
    fontWeight: FontWeight = FontWeight.Normal,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textAlign: TextAlign? = null,
) {
    MainText(
        modifier = modifier,
        text = text,
        size = size,
        color = color,
        fontWeight = fontWeight,
        maxLines = 1,
        overflow = overflow,
        textAlign = textAlign,
        textStyle = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
    )
}


@Composable
fun ItemTitle(text: String, modifier: Modifier = Modifier) {
    MainText(
        text = text,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        size = R.dimen.text_title.inDp(),
    )
}

@Composable
fun AuthorTitle(text: String, modifier: Modifier = Modifier) {
    SingleLineMainText(
        text = text,
        modifier = modifier,
        fontWeight = FontWeight.Normal,
        size = R.dimen.text_mini.inDp(),
    )
}

@Composable
fun DescTitle(text: String, modifier: Modifier = Modifier) {
    SingleLineMainText(
        text = text,
        modifier = modifier,
        fontWeight = FontWeight.Normal,
        size = R.dimen.text_mini.inDp(),
    )
}