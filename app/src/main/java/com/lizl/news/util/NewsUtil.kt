package com.lizl.news.util

import com.blankj.utilcode.util.Utils
import com.lizl.news.R
import com.lizl.news.constant.AppConstant

object NewsUtil
{
    fun getNewsAllPlatform() = listOf(AppConstant.NEWS_PLATFORM_ZHIHU, AppConstant.NEWS_PLATFORM_DAILY_HOT, AppConstant.NEWS_PLATFORM_HEADLINE)

    fun getNewsPlatformName(platform: String): String
    {
        val context = Utils.getApp()
        return when (platform)
        {
            AppConstant.NEWS_PLATFORM_ZHIHU     -> context.getString(R.string.zhihu)
            AppConstant.NEWS_PLATFORM_HEADLINE  -> context.getString(R.string.headline_news)
            AppConstant.NEWS_PLATFORM_DAILY_HOT -> context.getString(R.string.daily_hot)
            else                                -> ""
        }
    }

    fun getNewsSourceName(source: String): String
    {
        val context = Utils.getApp()
        return when (source)
        {
            AppConstant.NEWS_SOURCE_ZHIHU_DAILY            -> context.getString(R.string.zhihu_daily)
            AppConstant.NEWS_SOURCE_ZHIHU_TOP              -> context.getString(R.string.zhihu_top)
            AppConstant.NEWS_SOURCE_DAILY_BRIEFING         -> context.getString(R.string.daily_briefing)
            AppConstant.NEWS_SOURCE_HEADLINE_GAME          -> context.getString(R.string.game)
            AppConstant.NEWS_SOURCE_HEADLINE_ANIMATION     -> context.getString(R.string.animation)
            AppConstant.NEWS_SOURCE_HEADLINE_NBA           -> context.getString(R.string.nba)
            AppConstant.NEWS_SOURCE_HEADLINE_IT            -> context.getString(R.string.it)
            AppConstant.NEWS_SOURCE_HEADLINE_INTERNET      -> context.getString(R.string.internet)
            AppConstant.NEWS_SOURCE_HEADLINE_SCIENCE       -> context.getString(R.string.science)
            AppConstant.NEWS_SOURCE_HEADLINE_DOMESTIC      -> context.getString(R.string.domestic)
            AppConstant.NEWS_SOURCE_HEADLINE_INTERNATIONAL -> context.getString(R.string.international)
            AppConstant.NEWS_SOURCE_HEADLINE_SOCIAL        -> context.getString(R.string.social)
            else                                           -> ""
        }
    }

    fun getNewsSourceByPlatform(platform: String): List<String>
    {
        return when (platform)
        {
            AppConstant.NEWS_PLATFORM_ZHIHU     -> listOf(AppConstant.NEWS_SOURCE_ZHIHU_TOP, AppConstant.NEWS_SOURCE_ZHIHU_DAILY)
            AppConstant.NEWS_PLATFORM_HEADLINE  -> listOf(AppConstant.NEWS_SOURCE_HEADLINE_GAME, AppConstant.NEWS_SOURCE_HEADLINE_ANIMATION,
                    AppConstant.NEWS_SOURCE_HEADLINE_NBA, AppConstant.NEWS_SOURCE_HEADLINE_IT, AppConstant.NEWS_SOURCE_HEADLINE_INTERNET,
                    AppConstant.NEWS_SOURCE_HEADLINE_SCIENCE, AppConstant.NEWS_SOURCE_HEADLINE_DOMESTIC, AppConstant.NEWS_SOURCE_HEADLINE_INTERNATIONAL,
                    AppConstant.NEWS_SOURCE_HEADLINE_SOCIAL)
            AppConstant.NEWS_PLATFORM_DAILY_HOT -> listOf(AppConstant.NEWS_SOURCE_DAILY_BRIEFING)
            else                                -> emptyList()
        }
    }
}