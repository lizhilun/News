package com.lizl.news.mvvm.repository

import android.util.Log
import com.blankj.utilcode.util.GsonUtils
import com.lizl.news.constant.AppConstant
import com.lizl.news.model.NewsModel
import com.lizl.news.model.todayinhistory.TodayInHistoryResponseModel
import com.lizl.news.model.zhihu.DateBean
import com.lizl.news.util.HttpUtil

class TodayInHistoryRepository : NewsDataRepository
{
    companion object
    {
        private const val TAG = "TodayInHistoryRepository"
        private const val APP_KEY = "bd6ffcc2f7d02215dc7c0bed7420cc89"
    }

    override fun getLatestNews(): MutableList<NewsModel>
    {
        val curDate = DateBean(System.currentTimeMillis())

        Log.d(TAG, "getTodayInHistoryNews() called")
        val resultItem = HttpUtil.requestData("http://api.juheapi.com/japi/toh?key=${APP_KEY}&v=1.0&month=${curDate.month}&day=${curDate.day}")
        Log.d(TAG, "getTodayInHistoryNews() called with: result = [${resultItem.result}], data = [${resultItem.data}]")

        val newsList = mutableListOf<NewsModel>()

        try
        {
            val todayInHistoryResponseModel = GsonUtils.fromJson(resultItem.data, TodayInHistoryResponseModel::class.java)
            todayInHistoryResponseModel.resultList?.sortedByDescending { it.year }?.forEach {
                newsList.add(NewsModel(it.des, it.title, listOf(it.pic), AppConstant.NEWS_SOURCE_TODAY_IN_HISTORY))
            }
        }
        catch (e: Exception)
        {
            Log.e(TAG, "getTodayInHistoryNews error:", e)
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