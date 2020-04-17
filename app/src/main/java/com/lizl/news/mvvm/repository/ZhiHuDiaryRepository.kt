package com.lizl.news.mvvm.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.lizl.news.model.NewsModel
import com.lizl.news.util.HttpUtil

object ZhiHuDiaryRepository
{
    private val TAG = "ZhiHuDiaryRepository"

    fun getDiaryData(resultCallback: (Boolean, List<NewsModel>) -> Unit)
    {
        HttpUtil.requestData("http://news-at.zhihu.com/api/4/news/latest") { result, data ->
            Log.d(TAG, "getDiaryData() called with: result = [$result], data = [$data]")
            if (result)
            {
                try
                {
                    val diaryDataModel = Gson().fromJson(data, DiaryDataModel::class.java)
                    val newsList = mutableListOf<NewsModel>()
                    diaryDataModel.storyList?.forEach {
                        newsList.add(NewsModel(it.title, it.imageList?.first().orEmpty()))
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

    class DiaryDataModel
    {
        @SerializedName("date")
        val date = ""

        @SerializedName("stories")
        val storyList: List<DiaryStory>? = null
    }

    class DiaryStory
    {
        @SerializedName("image_hue")
        val imageHue = ""

        @SerializedName("title")
        val title = ""

        @SerializedName("url")
        val url = ""

        @SerializedName("hint")
        val hint = ""

        @SerializedName("ga_prefix")
        val gaPrefix = ""

        @SerializedName("images")
        val imageList: List<String>? = null

        @SerializedName("type")
        val type = 0

        @SerializedName("id")
        val id = ""
    }
}