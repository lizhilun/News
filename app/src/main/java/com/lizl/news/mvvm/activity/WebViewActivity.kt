package com.lizl.news.mvvm.activity

import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.lizl.news.R
import com.lizl.news.constant.AppConstant
import com.lizl.news.dao.AppDatabase
import com.lizl.news.databinding.ActivityWebviewBinding
import com.lizl.news.model.news.colletion.NewsCollectionModel
import com.lizl.news.mvvm.base.BaseActivity
import com.lizl.news.util.SkinUtil
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.coroutines.*

class WebViewActivity : BaseActivity<ActivityWebviewBinding>(R.layout.activity_webview)
{
    override fun initView()
    {
        val wSetting = webview.settings
        wSetting.javaScriptEnabled = true
        wSetting.databaseEnabled = true
        wSetting.domStorageEnabled = true
        wSetting.safeBrowsingEnabled = true
        wSetting.blockNetworkImage = false

        if (SkinUtil.isNightModeOn() && WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK))
        {
            WebSettingsCompat.setForceDark(wSetting, WebSettingsCompat.FORCE_DARK_ON)
        }

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
    }

    override fun initData()
    {
        val newsTitle = intent?.getStringExtra(AppConstant.BUNDLE_NEWS_TITLE)
        val newsSource = intent?.getStringExtra(AppConstant.BUNDLE_NEWS_SOURCE).orEmpty()
        val detailUrl = when
        {
            newsTitle?.isNotBlank() == true -> intent?.getStringExtra(AppConstant.BUNDLE_NEWS_URL)
            else                            -> intent?.getStringExtra(AppConstant.BUNDLE_URL)
        }.orEmpty()

        Log.d(TAG, "initView() detailUrl：${detailUrl}")

        webview.loadUrl(detailUrl)

        val isNewsMode = newsTitle?.isNotBlank() == true && detailUrl.isNotBlank()

        fab_collect.isVisible = isNewsMode

        if (isNewsMode)
        {
            var collectionModel: NewsCollectionModel? = null

            AppDatabase.instance.getNewsCollectionDao().findCollection(newsTitle.orEmpty(), newsSource).observe(this, Observer {
                collectionModel = it
                fab_collect.isSelected = it != null
            })

            fab_collect.setOnClickListener {
                if (fab_collect.isSelected && collectionModel != null)
                {
                    AppDatabase.instance.getNewsCollectionDao().delete(collectionModel!!)
                }
                else
                {
                    collectionModel = NewsCollectionModel(newsSource = newsSource, newsTitle = newsTitle.orEmpty(), newsUrl = detailUrl,
                            collectionTime = System.currentTimeMillis()).apply {
                        AppDatabase.instance.getNewsCollectionDao().insert(this)
                    }
                }
            }
        }
    }
}