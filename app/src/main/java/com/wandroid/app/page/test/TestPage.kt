package com.wandroid.app.page.test

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wandroid.app.R
import com.wandroid.app.util.navigation.BaseEmptyArgNavPath
import com.wandroid.app.ui.skeleton.skeleton
import com.wandroid.app.ui.theme.WandroidTheme
import com.wandroid.app.ui.widget.Hanging
import com.wandroid.app.ui.widget.MainLogo
import com.wandroid.app.util.Mark
import com.wandroid.app.util.inString

object TestNav : BaseEmptyArgNavPath() {
    override val path: String
        get() = "test"
}

@Composable
fun TestPage(
    controller: NavHostController,
    entry: NavBackStackEntry,
) {
    Greeting(R.string.app_name.inString())
}

@Composable
private fun Greeting(name: String) {
    Column(modifier = Modifier.statusBarsPadding()) {
        Text(
            text = "Hello $name!"
        )
        var mark: Mark? by remember { mutableStateOf(null) }
        MainLogo(Modifier.size(200.dp), logoAnimation = Hanging)
        Button(onClick = {
            mark = Mark()
        }) {
            Text("shake")
        }

        Spacer(modifier = Modifier
            .size(width = 400.dp, height = 100.dp)
            .clip(RoundedCornerShape(6.dp))
            .skeleton()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    WandroidTheme {
        Greeting("Android")
    }
}