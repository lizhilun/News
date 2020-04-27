package com.lizl.news.util

import com.blankj.utilcode.util.Utils
import com.lizl.news.R
import com.lizl.news.constant.AppConstant

object NewsUtil
{
    fun getNewsAllSources() = listOf(AppConstant.NEWS_PLATFORM_ZHIHU_DIARY, AppConstant.NEWS_PLATFORM_ZHIHU_TOP, AppConstant.NEWS_PLATFORM_HEADLINE)

    fun getNewsSourceName(source: String): String
    {
        val context = Utils.getApp()
        return when (source)
        {
            AppConstant.NEWS_PLATFORM_ZHIHU_DIARY -> context.getString(R.string.zhihu_diary)
            AppConstant.NEWS_PLATFORM_ZHIHU_TOP   -> context.getString(R.string.zhihu_top)
            AppConstant.NEWS_PLATFORM_HEADLINE    -> context.getString(R.string.headline_news)
            else                                  -> ""
        }
    }
}