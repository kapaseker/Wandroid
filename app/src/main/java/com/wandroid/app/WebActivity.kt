package com.wandroid.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.wandroid.app.ui.theme.WandroidTheme
import com.wandroid.app.ui.widget.drawBackground
import com.wandroid.app.util.emptyString
import com.wandroid.app.util.inColor


private const val KEY_URL = "URL"

class WebActivity : ComponentActivity() {

    companion object {
        /**
         * make a web Activity
         *
         * @param context
         * @param url
         * @return
         */
        fun makeIntent(context: Context, url: String): Intent {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(KEY_URL, url)
            return intent
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val url = intent.getStringExtra(KEY_URL).orEmpty()

        setContent {

            WandroidTheme {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .drawBackground(R.color.background.inColor())
                ) {

                    var title by remember { mutableStateOf(emptyString()) }

                    AndroidView(modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxSize(), factory = {
                        WebView(it).also { webView ->
                            webView.layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                            )
                            webView.settings.let {
                                it.javaScriptEnabled = true
                                it.loadWithOverviewMode = true
                                it.userAgentString = "User-Agent:Android"
                                it.defaultTextEncodingName = "UTF-8"
                                it.javaScriptCanOpenWindowsAutomatically = true
                                it.domStorageEnabled = true
                                it.allowFileAccess = true
                                it.allowFileAccess = true
                            }

                            webView.webViewClient = object : WebViewClient() {
                                override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
//                                    if (request.url.scheme == "http" || request.url.scheme == "https") {
//                                    }
                                    view.loadUrl(request.url.toString())
                                    return true
                                }

                                override fun onPageFinished(view: WebView, url: String) {
                                    title = view.getTitle().toString()
                                }
                            }
                            if (url.isNotEmpty()) {
                                webView.loadUrl(url)
                            }
                        }
                    }, onRelease = {
                        it.removeAllViews()
                        it.destroy()
                    })
                }
            }
        }
    }
}