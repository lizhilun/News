package com.lizl.news.mvvm.activity

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.lizl.news.R
import com.lizl.news.constant.AppConstant
import com.lizl.news.databinding.ActivityHeadlineDetailBinding
import com.lizl.news.mvvm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_headline_detail.*

class HeadlineDetailActivity : BaseActivity<ActivityHeadlineDetailBinding>(R.layout.activity_headline_detail)
{
    override fun initView()
    {
        val detailUrl = intent?.getStringExtra(AppConstant.BUNDLE_DATA_STRING).orEmpty()

        val wSetting = webview.settings
        wSetting.javaScriptEnabled = true

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

                if (url.isBlank()) return true

                return super.shouldOverrideUrlLoading(view, url)
            }
        }

        webview.loadUrl(detailUrl)
    }
}