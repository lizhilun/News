package com.lizl.news.mvvm.repository

import android.util.Log
import com.blankj.utilcode.util.GsonUtils
import com.lizl.news.constant.AppConstant
import com.lizl.news.model.NewsModel
import com.lizl.news.model.headline.HeadlineResponseModel
import com.lizl.news.util.HttpUtil
import org.jsoup.Jsoup

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

    override fun getNewsDetail(diaryUrl: String): String?
    {
        Log.d(TAG, "getNewsDetail() called with: diaryUrl = [$diaryUrl]")
        try
        {
            val doc = Jsoup.connect(diaryUrl).get()
            val content = doc.getElementsByTag("html").toString()
            Log.d(TAG, "getNewsDetail() called with: content = [$content]")
            return content
        }
        catch (e: Exception)
        {
            Log.e(TAG, "getNewsDetail error:", e)
        }
        return null
    }

    private fun getHeadlineData(pageIndex: Int): MutableList<NewsModel>
    {
        Log.d(TAG, "getHeadlineData() called with: pageIndex = [$pageIndex]")
        val resultItem =
                HttpUtil.requestData("http://api.tianapi.com/${getNewsApiTypeFromSource(newsSource)}/index?Array&key=${APP_KEY}&num=20&page=${pageIndex}")
        Log.d(TAG, "getHeadlineData() called with: result = [${resultItem.result}], data = [${resultItem.data}]")

        val newsList = mutableListOf<NewsModel>()

        try
        {
            val headlineResponseModel = GsonUtils.fromJson(resultItem.data, HeadlineResponseModel::class.java)
            headlineResponseModel.newsList?.forEach {
                newsList.add(NewsModel(it.url, it.title, listOf(it.picUrl), newsSource))
            }
        }
        catch (e: Exception)
        {
            Log.e(TAG, "getHeadlineData error:", e)
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