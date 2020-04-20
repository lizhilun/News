package com.lizl.news.mvvm.repository

import android.util.Log
import com.google.gson.Gson
import com.lizl.news.model.NewsDetailModel
import com.lizl.news.model.NewsModel
import com.lizl.news.model.zhihu.DateBean
import com.lizl.news.model.zhihu.DiaryDataModel
import com.lizl.news.model.zhihu.DiaryDetailModel
import com.lizl.news.util.HttpUtil
import org.jsoup.Jsoup

object ZhiHuDiaryRepository
{
    private val TAG = "ZhiHuDiaryRepository"

    private lateinit var curDataDate: DateBean

    fun getLatestDiaryData(): MutableList<NewsModel>
    {
        curDataDate = DateBean(System.currentTimeMillis())
        val latestDiaryList = mutableListOf<NewsModel>()
        latestDiaryList.addAll(getDiaryData("http://news-at.zhihu.com/api/4/news/latest"))
        latestDiaryList.addAll(getBeforeDiaryData())
        return latestDiaryList
    }

    fun getBeforeDiaryData(): MutableList<NewsModel>
    {
        curDataDate = curDataDate.lastDay()
        return getDiaryData("http://news-at.zhihu.com/api/4/news/before/${curDataDate.getDateInfo()}")
    }

    fun getDiaryDetail(diaryUrl: String): NewsDetailModel?
    {
        Log.d(TAG, "getDiaryDetail() called with: diaryUrl = [$diaryUrl]")
        val resultItem = HttpUtil.requestData(diaryUrl)
        Log.d(TAG, "getDiaryDetail() called with: result = [${resultItem.result}], data = [${resultItem.data}]")
        if (resultItem.result)
        {
            try
            {
                val diaryDetailModel = Gson().fromJson(resultItem.data, DiaryDetailModel::class.java)
                val doc = Jsoup.parse(diaryDetailModel.body)
                val content = doc.getElementsByClass("content")
                return NewsDetailModel(diaryDetailModel.title, content.toString())
            }
            catch (e: Exception)
            {
                Log.e(TAG, "getDiaryDetail error:", e)
            }
        }
        return null
    }

    private fun getDiaryData(url: String): MutableList<NewsModel>
    {
        Log.d(TAG, "getDiaryData() called with: url = [$url]")
        val resultItem = HttpUtil.requestData(url)
        Log.d(TAG, "getDiaryData() called with: result = [${resultItem.result}], data = [${resultItem.data}]")
        val newsList = mutableListOf<NewsModel>()
        if (resultItem.result)
        {
            try
            {
                val diaryDataModel = Gson().fromJson(resultItem.data, DiaryDataModel::class.java)
                diaryDataModel.storyList?.forEach {
                    newsList.add(NewsModel("http://news-at.zhihu.com/api/4/news/${it.id}", it.title, it.imageList?.first().orEmpty()))
                }
            }
            catch (e: Exception)
            {
                Log.e(TAG, "getDiaryData error:", e)
            }
        }
        return newsList
    }
}