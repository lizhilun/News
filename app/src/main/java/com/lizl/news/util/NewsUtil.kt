package com.lizl.news.util

import com.blankj.utilcode.util.Utils
import com.lizl.news.R
import com.lizl.news.constant.AppConstant

object NewsUtil
{
    fun getNewsAllPlatform() = listOf(AppConstant.NEWS_PLATFORM_ZHIHU, AppConstant.NEWS_PLATFORM_DAILY_HOT)

    fun getNewsPlatformName(platform: String): String
    {
        val context = Utils.getApp()
        return when (platform)
        {
            AppConstant.NEWS_PLATFORM_ZHIHU     -> context.getString(R.string.zhihu)
            AppConstant.NEWS_PLATFORM_DAILY_HOT -> context.getString(R.string.daily_hot)
            else                                -> ""
        }
    }

    fun getNewsSourceName(source: String): String
    {
        val context = Utils.getApp()
        return when (source)
        {
            AppConstant.NEWS_SOURCE_ZHIHU_DAILY     -> context.getString(R.string.zhihu_daily)
            AppConstant.NEWS_SOURCE_ZHIHU_TOP       -> context.getString(R.string.zhihu_top)
            AppConstant.NEWS_SOURCE_DAILY_36_KR     -> context.getString(R.string._36_kr)
            AppConstant.NEWS_SOURCE_DAILY_THE_PAPER -> context.getString(R.string.the_paper)
            AppConstant.NEWS_SOURCE_DAILY_IT_HOME   -> context.getString(R.string.it_home)
            AppConstant.NEWS_SOURCE_DAILY_WEIBO     -> context.getString(R.string.weibo)
            AppConstant.NEWS_SOURCE_DAILY_SS_PAI    -> context.getString(R.string.ss_pai)
            AppConstant.NEWS_SOURCE_DAILY_HU_XIU    -> context.getString(R.string.hu_xiu)
            AppConstant.NEWS_SOURCE_DAILY_JIAN_DAN  -> context.getString(R.string.jian_dan)
            AppConstant.NEWS_SOURCE_DAILY_HU_PU     -> context.getString(R.string.hu_pu)
            else                                    -> ""
        }
    }

    fun getNewsSourceByPlatform(platform: String): List<String>
    {
        return when (platform)
        {
            AppConstant.NEWS_PLATFORM_ZHIHU     -> listOf(AppConstant.NEWS_SOURCE_ZHIHU_TOP, AppConstant.NEWS_SOURCE_ZHIHU_DAILY)
            AppConstant.NEWS_PLATFORM_DAILY_HOT -> listOf(AppConstant.NEWS_SOURCE_DAILY_36_KR, AppConstant.NEWS_SOURCE_DAILY_IT_HOME,
                    AppConstant.NEWS_SOURCE_DAILY_SS_PAI, AppConstant.NEWS_SOURCE_DAILY_HU_XIU, AppConstant.NEWS_SOURCE_DAILY_JIAN_DAN,
                    AppConstant.NEWS_SOURCE_DAILY_HU_PU, AppConstant.NEWS_SOURCE_DAILY_THE_PAPER, AppConstant.NEWS_SOURCE_DAILY_WEIBO)
            else                                -> emptyList()
        }
    }
}