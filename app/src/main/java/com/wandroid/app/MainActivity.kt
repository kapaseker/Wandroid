package com.wandroid.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.wandroid.app.page.home.HomeNav
import com.wandroid.app.page.home.HomePage
import com.wandroid.app.page.test.TestNav
import com.wandroid.app.page.test.TestPage
import com.wandroid.app.ui.components.AppTheme
import com.wandroid.app.ui.theme.WandroidTheme
import com.wandroid.app.ui.widget.drawBackground
import com.wandroid.app.util.navigation.EmptyArg
import com.wandroid.app.util.navigation.composablePage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {

                WandroidTheme {

                    Surface(
                        modifier = Modifier.drawBackground(Color.White)
                    ) {

                        val controller = rememberNavController()

                        NavHost(
                            navController = controller,
                            startDestination = HomeNav.make(EmptyArg),
                        ) {
                            composablePage(
                                nav = HomeNav
                            ) { entry ->
                                HomePage(
                                    controller = controller,
                                    entry = entry,
                                )
                            }

                            composablePage(
                                nav = TestNav
                            ) { entry ->
                                TestPage(
                                    controller = controller,
                                    entry = entry,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}