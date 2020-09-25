package com.lizl.news.util

import android.app.Activity
import android.content.Intent
import com.blankj.utilcode.util.ActivityUtils
import com.lizl.news.constant.AppConstant
import com.lizl.news.mvvm.activity.WebViewActivity
import com.lizl.news.mvvm.activity.ZhiHuDetailActivity
import com.lizl.news.mvvm.activity.ZhiHuTopDetailActivity
import java.io.Serializable

object ActivityUtil
{
    fun turnToNewsDetailActivity(newsSource: String, newTitle: String, newsUrl: String)
    {
        turnToActivity(when (newsSource)
        {
            AppConstant.NEWS_SOURCE_ZHIHU_DAILY -> ZhiHuDetailActivity::class.java
            AppConstant.NEWS_SOURCE_ZHIHU_TOP   -> ZhiHuTopDetailActivity::class.java
            else                                -> WebViewActivity::class.java
        }, Pair(AppConstant.BUNDLE_NEWS_TITLE, newTitle), Pair(AppConstant.BUNDLE_NEWS_URL, newsUrl), Pair(AppConstant.BUNDLE_NEWS_SOURCE, newsSource))
    }

    fun turnToActivity(cls: Class<out Activity>, vararg extraList: Pair<String, Any>)
    {
        ActivityUtils.finishActivity(cls)
        val topActivity = ActivityUtils.getTopActivity() ?: return
        val intent = Intent(topActivity, cls)

        extraList.forEach {
            when (it.second)
            {
                is Int          -> intent.putExtra(it.first, it.second as Int)
                is String       -> intent.putExtra(it.first, it.second as String)
                is Boolean      -> intent.putExtra(it.first, it.second as Boolean)
                is ArrayList<*> -> intent.putExtra(it.first, it.second as ArrayList<*>)
                is Serializable -> intent.putExtra(it.first, it.second as Serializable)
            }
        }

        topActivity.startActivity(intent)
    }
}