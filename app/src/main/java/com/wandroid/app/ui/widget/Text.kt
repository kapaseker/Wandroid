package com.wandroid.app.ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wandroid.app.ui.components.LocalTypography

@Composable
fun ItemTitle(text: String, modifier: Modifier = Modifier) {
    com.wandroid.app.ui.components.components.Text(
        text = text,
        modifier = modifier,
        style = LocalTypography.current.h2
    )
}

@Composable
fun AuthorTitle(text: String, modifier: Modifier = Modifier) {
    com.wandroid.app.ui.components.components.Text(
        text = text,
        modifier = modifier,
        style = LocalTypography.current.label2
    )
}

@Composable
fun DescTitle(text: String, modifier: Modifier = Modifier) {
    com.wandroid.app.ui.components.components.Text(
        text = text,
        modifier = modifier,
        style = LocalTypography.current.label2
    )
}