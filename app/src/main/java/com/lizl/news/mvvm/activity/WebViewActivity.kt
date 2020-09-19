package com.lizl.news.mvvm.activity

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.lizl.news.R
import com.lizl.news.constant.AppConstant
import com.lizl.news.databinding.ActivityWebviewBinding
import com.lizl.news.mvvm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*

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

        webview.loadUrl(detailUrl)
    }
}