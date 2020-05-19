package com.lizl.news.mvvm.repository

import com.lizl.news.constant.AppConstant
import com.lizl.news.model.news.NewsModel
import com.lizl.news.model.news.dailyhot.briefing.DailyBriefingResponseModel
import com.lizl.news.util.HttpUtil

class DailyBriefingRepository : NewsDataRepository
{
    companion object
    {
        private const val TAG = "HeadlineRepository"
        private const val APP_KEY = "da06fe050a4451aad13188e4b4d145be"
    }

    override fun getLatestNews(): MutableList<NewsModel>
    {
        val requestUrl = "http://api.tianapi.com/bulletin/index?key=${APP_KEY}"

        val newsList = mutableListOf<NewsModel>()

        val dailyBriefingResponseModel = HttpUtil.requestData(requestUrl, DailyBriefingResponseModel::class.java)
        dailyBriefingResponseModel?.newsList?.forEach {
            newsList.add(NewsModel(it.url, it.title, it.imgsrc, AppConstant.NEWS_SOURCE_DAILY_BRIEFING))
        }

        return newsList
    }

    override fun loadMoreNews() = mutableListOf<NewsModel>()

    override fun getNewsDetail(dailyUrl: String) = ""

    override fun canLoadMore() = false
}