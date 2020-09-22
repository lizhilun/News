package com.lizl.news.mvvm.activity

import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.lizl.news.R
import com.lizl.news.constant.AppConstant
import com.lizl.news.databinding.ActivityWebviewBinding
import com.lizl.news.mvvm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.coroutines.*

class WebViewActivity : BaseActivity<ActivityWebviewBinding>(R.layout.activity_webview)
{
    override fun initView()
    {
        val detailUrl = intent?.getStringExtra(AppConstant.BUNDLE_DATA_STRING).orEmpty()

        Log.d(TAG, "initView() detailUrl：${detailUrl}")

        val wSetting = webview.settings
        wSetting.javaScriptEnabled = true
        wSetting.databaseEnabled = true
        wSetting.domStorageEnabled = true
        wSetting.safeBrowsingEnabled = true
        wSetting.blockNetworkImage = false

        npb_loading_progress.progress = 0

        var dismissProgressBarJob: Job? = null

        webview.webViewClient = object : WebViewClient()
        {
            override fun onPageFinished(view: WebView, url: String)
            {
                Log.d(TAG, "onPageFinished:$url")

                super.onPageFinished(view, url)
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean
            {
                Log.d(TAG, "shouldOverrideUrlLoading:$url")

                return super.shouldOverrideUrlLoading(view, url)
            }
        }

        webview.webChromeClient = object : WebChromeClient()
        {
            override fun onProgressChanged(view: WebView?, newProgress: Int)
            {
                npb_loading_progress.progress = newProgress
                dismissProgressBarJob?.cancel()
                if (newProgress == 100)
                {
                    dismissProgressBarJob = GlobalScope.launch(Dispatchers.Main) {
                        delay(200)
                        npb_loading_progress.isVisible = false
                    }
                }
                else
                {
                    npb_loading_progress.isVisible = true
                }
                super.onProgressChanged(view, newProgress)
            }
        }

        webview.loadUrl(detailUrl)
    }
}