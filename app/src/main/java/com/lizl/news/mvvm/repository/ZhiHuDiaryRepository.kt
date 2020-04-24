package com.lizl.news.mvvm.repository

import android.util.Log
import com.google.gson.Gson
import com.lizl.news.model.AuthorModel
import com.lizl.news.model.NewsModel
import com.lizl.news.model.zhihu.*
import com.lizl.news.util.HttpUtil
import org.jsoup.Jsoup

object ZhiHuDiaryRepository
{
    private val TAG = "ZhiHuDiaryRepository"

    private lateinit var curDataDate: DateBean

    fun getLatestDiaryData(): MutableList<NewsModel>
    {
        curDataDate = DateBean(System.currentTimeMillis())
        return getDiaryData("http://news-at.zhihu.com/api/4/news/latest")
    }

    fun getBeforeDiaryData(): MutableList<NewsModel>
    {
        curDataDate = curDataDate.lastDay()
        return getDiaryData("http://news-at.zhihu.com/api/4/news/before/${curDataDate.getDateInfo()}")
    }

    fun getDiaryDetail(diaryUrl: String): ZhiHuDiaryDetailModel?
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

                val questionTitleElements = doc.getElementsByClass("question-title")
                val authorNameElements = doc.getElementsByClass("author")
                val authorAvatarElements = doc.getElementsByClass("avatar")
                val authorBioElements = doc.getElementsByClass("bio")
                val contentElements = doc.getElementsByClass("content")

                val questionList = mutableListOf<ZhiHuQuestionModel>()
                val size = questionTitleElements.size.coerceAtMost(
                    authorNameElements.size.coerceAtMost(authorAvatarElements.size.coerceAtMost(authorBioElements.size.coerceAtMost(contentElements.size)))
                )

                for (index in 0 until size)
                {
                    questionList.add(
                        ZhiHuQuestionModel(
                            if (size == 1) "" else "Q：${questionTitleElements[index].text()}",
                            contentElements[index].toString(),
                            AuthorModel(authorNameElements[index].text(), authorAvatarElements[index].attr("src"), authorBioElements[index].text())
                        )
                    )
                }

                return ZhiHuDiaryDetailModel(diaryDetailModel.title, diaryDetailModel.images?.first(), questionList)
            } catch (e: Exception)
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
            } catch (e: Exception)
            {
                Log.e(TAG, "getDiaryData error:", e)
            }
        }
        return newsList
    }
}