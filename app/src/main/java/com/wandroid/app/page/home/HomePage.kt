package com.wandroid.app.page.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.wandroid.app.R
import com.wandroid.app.ext.navigation.BaseEmptyArgNavPath
import com.wandroid.app.page.home.business.HomeViewModel
import com.wandroid.app.ui.skeleton.skeleton
import com.wandroid.app.ui.theme.WandroidTheme
import com.wandroid.app.ui.widget.MainLogo
import com.wandroid.app.util.Mark
import com.wandroid.app.util.inString
import kotlinx.coroutines.CoroutineScope

object HomeNav : BaseEmptyArgNavPath() {
    override val path: String
        get() = "home"
}

@Composable
fun HomePage(
    controller: NavHostController,
    entry: NavBackStackEntry,
    viewModel: HomeViewModel = viewModel(HomeViewModel::class),
    scope: CoroutineScope = rememberCoroutineScope(),
) {
}