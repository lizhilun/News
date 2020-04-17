package com.lizl.news.mvvm.repository

import android.util.Log
import com.google.gson.Gson
import com.lizl.news.model.NewsDetailModel
import com.lizl.news.model.NewsModel
import com.lizl.news.model.zhihu.DateBean
import com.lizl.news.model.zhihu.DiaryDataModel
import com.lizl.news.model.zhihu.DiaryDetailModel
import com.lizl.news.util.HttpUtil

object ZhiHuDiaryRepository
{
    private val TAG = "ZhiHuDiaryRepository"

    private lateinit var curDataDate: DateBean

    fun getLatestDiaryData(resultCallback: (Boolean, List<NewsModel>) -> Unit)
    {
        curDataDate = DateBean(System.currentTimeMillis())
        getDiaryData("http://news-at.zhihu.com/api/4/news/latest", resultCallback)
    }

    fun getBeforeDiaryData(resultCallback: (Boolean, List<NewsModel>) -> Unit)
    {
        curDataDate = curDataDate.lastDay()
        getDiaryData("http://news-at.zhihu.com/api/4/news/before/${curDataDate.getDateInfo()}", resultCallback)
    }

    fun getDiaryDetail(diaryUrl: String, resultCallback: (Boolean, NewsDetailModel?) -> Unit)
    {
        Log.d(TAG, "getDiaryDetail() called with: diaryUrl = [$diaryUrl]")
        HttpUtil.requestData(diaryUrl) { result, data ->
            Log.d(TAG, "getDiaryDetail() called with: result = [$result], data = [$data]")
            if (result)
            {
                val diaryDetailModel = Gson().fromJson(data, DiaryDetailModel::class.java)
                resultCallback.invoke(true, NewsDetailModel(diaryDetailModel.title, diaryDetailModel.body))
            }
        }
    }

    private fun getDiaryData(url: String, resultCallback: (Boolean, List<NewsModel>) -> Unit)
    {
        Log.d(TAG, "getDiaryData() called with: url = [$url]")

        HttpUtil.requestData(url) { result, data ->
            Log.d(TAG, "getDiaryData() called with: result = [$result], data = [$data]")
            if (result)
            {
                try
                {
                    val diaryDataModel = Gson().fromJson(data, DiaryDataModel::class.java)
                    val newsList = mutableListOf<NewsModel>()
                    diaryDataModel.storyList?.forEach {
                        newsList.add(NewsModel("http://news-at.zhihu.com/api/4/news/${it.id}", it.title, it.imageList?.first().orEmpty()))
                    }
                    resultCallback.invoke(true, newsList)
                }
                catch (e: Exception)
                {
                    resultCallback.invoke(false, emptyList())
                }
            }
            else
            {
                resultCallback.invoke(false, emptyList())
            }
        }
    }
}