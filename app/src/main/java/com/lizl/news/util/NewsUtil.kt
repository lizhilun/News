package com.lizl.news.util

import android.text.TextUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.google.gson.Gson
import com.lizl.news.R
import com.lizl.news.constant.AppConstant
import com.lizl.news.model.other.NewsSourceModel

object NewsUtil
{
    private const val KEY_NEWS_SOURCES = "KEY_NEWS_SOURCES"

    private val gson: Gson by lazy { Gson() }

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

    fun getVisibleNewsSource(): List<NewsSourceModel>
    {
        return getAllNewsSource().filter { it.visible }
    }

    fun getAllNewsSource(): List<NewsSourceModel>
    {
        val json = SPUtils.getInstance().getString(KEY_NEWS_SOURCES)
        if (TextUtils.isEmpty(json))
        {
            val sourceList = listOf(AppConstant.NEWS_SOURCE_ZHIHU_TOP, AppConstant.NEWS_SOURCE_ZHIHU_DAILY, AppConstant.NEWS_SOURCE_DAILY_GAME_CORE,
                    AppConstant.NEWS_SOURCE_DAILY_YYS, AppConstant.NEWS_SOURCE_DAILY_36_KR, AppConstant.NEWS_SOURCE_DAILY_IT_HOME,
                    AppConstant.NEWS_SOURCE_DAILY_SS_PAI, AppConstant.NEWS_SOURCE_DAILY_HU_XIU, AppConstant.NEWS_SOURCE_DAILY_JIAN_DAN,
                    AppConstant.NEWS_SOURCE_DAILY_HU_PU, AppConstant.NEWS_SOURCE_DAILY_THE_PAPER, AppConstant.NEWS_SOURCE_DAILY_V2EX,
                    AppConstant.NEWS_SOURCE_DAILY_WEIBO)

            return mutableListOf<NewsSourceModel>().apply {
                var index = 0
                sourceList.forEach { add(NewsSourceModel(it, index++, true)) }
                saveNewsSources(this)
            }
        }
        return gson.fromJson<Array<NewsSourceModel>>(json, Array<NewsSourceModel>::class.java).sortedBy { it.index }.toList()
    }

    fun saveNewsSources(newsSources: List<NewsSourceModel>)
    {
        SPUtils.getInstance().put(KEY_NEWS_SOURCES, gson.toJson(newsSources))
    }
}