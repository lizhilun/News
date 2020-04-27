package com.lizl.news.mvvm.repository

import android.util.Log
import com.blankj.utilcode.util.GsonUtils
import com.lizl.news.constant.AppConstant
import com.lizl.news.model.NewsModel
import com.lizl.news.model.zhihutop.ZhiHuTopResponseModel
import com.lizl.news.util.HttpUtil

class ZhiHuTopRepository : NewsDataRepository
{
    private val TAG = "ZhiHuTopRepository"

    override fun getLatestNews(): MutableList<NewsModel>
    {
        Log.d(TAG, "getLatestZhiHuTopNews() called")
        val resultItem = HttpUtil.requestData("https://www.zhihu.com/api/v3/feed/topstory/hot-lists/total?limit=50&desktop=true")
        Log.d(TAG, "getLatestZhiHuTopNews() called with: result = [${resultItem.result}], data = [${resultItem.data}]")

        val newsList = mutableListOf<NewsModel>()

        try
        {
            val zhiHuTopResponseModel = GsonUtils.fromJson(resultItem.data, ZhiHuTopResponseModel::class.java)
            zhiHuTopResponseModel.dataList?.forEach {
                it.target ?: return@forEach
                newsList.add(NewsModel("https://www.zhihu.com/question/${it.target.id}", it.target.title,
                        listOf(it.children?.first()?.thumbnail.orEmpty()), AppConstant.NEWS_PLATFORM_ZHIHU_TOP))
            }
        }
        catch (e: Exception)
        {
            Log.e(TAG, "getLatestHeadlineData error:", e)
        }

        return newsList
    }

    override fun loadMoreNews(): MutableList<NewsModel>
    {
        return mutableListOf()
    }

    override fun getNewsDetail(diaryUrl: String): Any?
    {
        return ""
    }

    override fun canLoadMore() = false
}