package com.lizl.news.mvvm.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.lizl.news.model.NewsModel
import com.lizl.news.util.HttpUtil
import java.util.*

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
                        newsList.add(NewsModel(it.id, it.title, it.imageList?.first().orEmpty()))
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

    class DateBean(private val time: Long)
    {
        private var year = 0
        private var month = 0
        private var day = 0

        init
        {
            val calendar = Calendar.getInstance()
            calendar.time = Date(time)
            year = calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH) + 1
            day = calendar.get(Calendar.DAY_OF_MONTH)
        }

        fun lastDay(): DateBean
        {
            return DateBean(time - 24 * 60 * 60 * 1000)
        }

        fun getDateInfo() = String.format(Locale.getDefault(), "%d%02d%02d", year, month, day)
    }
}