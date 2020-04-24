package com.lizl.news.mvvm.repository

import android.util.Log
import com.blankj.utilcode.util.GsonUtils
import com.lizl.news.constant.AppConstant
import com.lizl.news.model.NewsModel
import com.lizl.news.model.headline.HeadlineResponseModel
import com.lizl.news.util.HttpUtil
import org.jsoup.Jsoup

class HeadlineRepository : NewsDataRepository
{
    companion object
    {
        private const val TAG = "HeadlineRepository"
        private const val APP_KEY = "20530621f95eb23d45be7dfcb8b5f33d"
    }

    override fun getLatestNews(): MutableList<NewsModel>
    {
        Log.d(TAG, "getLatestHeadlineData() called")
        val resultItem = HttpUtil.requestData("http://v.juhe.cn/toutiao/index?type=top&key=${APP_KEY}")
        Log.d(TAG, "getLatestHeadlineData() called with: result = [${resultItem.result}], data = [${resultItem.data}]")

        val newsList = mutableListOf<NewsModel>()

        try
        {
            val headlineResponseModel = GsonUtils.fromJson(resultItem.data, HeadlineResponseModel::class.java)
            headlineResponseModel.result?.headlineList?.forEach {
                newsList.add(NewsModel(it.url, it.title, it.thumbnail_pic_s, AppConstant.NEWS_PLATFORM_HEADLINE))
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
}