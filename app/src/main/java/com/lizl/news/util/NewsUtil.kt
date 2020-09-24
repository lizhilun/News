package com.lizl.news.util

import com.blankj.utilcode.util.Utils
import com.lizl.news.R
import com.lizl.news.constant.AppConstant

object NewsUtil
{
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
            AppConstant.NEWS_SOURCE_DAILY_V2EX      -> context.getString(R.string.v2ex)
            AppConstant.NEWS_SOURCE_DAILY_GAME_CORE -> context.getString(R.string.game_core)
            AppConstant.NEWS_SOURCE_DAILY_YYS       -> context.getString(R.string.yys)
            else                                    -> ""
        }
    }
}