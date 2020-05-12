package com.lizl.news.mvvm.repository

import android.util.Log
import com.lizl.news.constant.AppConstant
import com.lizl.news.model.news.NewsModel
import com.lizl.news.model.news.headline.HeadlineResponseModel
import com.lizl.news.util.HttpUtil

class HeadlineRepository(private val newsSource: String) : NewsDataRepository
{
    companion object
    {
        private const val TAG = "HeadlineRepository"
        private const val APP_KEY = "da06fe050a4451aad13188e4b4d145be"
    }

    private var curPage = 1

    override fun getLatestNews(): MutableList<NewsModel>
    {
        curPage = 1
        return getHeadlineData(curPage)
    }

    override fun loadMoreNews(): MutableList<NewsModel>
    {
        curPage++
        return getHeadlineData(curPage)
    }

    override fun getNewsDetail(diaryUrl: String) = ""

    private fun getHeadlineData(pageIndex: Int): MutableList<NewsModel>
    {
        Log.d(TAG, "getHeadlineData() called with: pageIndex = [$pageIndex]")

        val requestUrl = "http://api.tianapi.com/${getNewsApiTypeFromSource(newsSource)}/index?Array&key=${APP_KEY}&num=20&page=${pageIndex}"

        val newsList = mutableListOf<NewsModel>()

        val headlineResponseModel = HttpUtil.requestData(requestUrl, HeadlineResponseModel::class.java)
        headlineResponseModel?.newsList?.forEach {
            newsList.add(NewsModel(it.url, it.title, listOf(it.picUrl), newsSource))
        }

        return newsList
    }

    private fun getNewsApiTypeFromSource(newsSource: String): String
    {
        return when (newsSource)
        {
            AppConstant.NEWS_SOURCE_HEADLINE_GAME          -> "game"
            AppConstant.NEWS_SOURCE_HEADLINE_ANIMATION     -> "dongman"
            AppConstant.NEWS_SOURCE_HEADLINE_NBA           -> "nba"
            AppConstant.NEWS_SOURCE_HEADLINE_IT            -> "it"
            AppConstant.NEWS_SOURCE_HEADLINE_INTERNET      -> "internet"
            AppConstant.NEWS_SOURCE_HEADLINE_SCIENCE       -> "keji"
            AppConstant.NEWS_SOURCE_HEADLINE_DOMESTIC      -> "guonei"
            AppConstant.NEWS_SOURCE_HEADLINE_INTERNATIONAL -> "world"
            AppConstant.NEWS_SOURCE_HEADLINE_SOCIAL        -> "social"
            else                                           -> ""
        }
    }

    override fun canLoadMore() = true
}