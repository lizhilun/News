package com.lizl.news.mvvm.repository

import android.util.Log
import com.lizl.news.constant.AppConstant
import com.lizl.news.model.AuthorModel
import com.lizl.news.model.NewsModel
import com.lizl.news.model.zhihu.*
import com.lizl.news.util.HttpUtil
import org.jsoup.Jsoup

class ZhiHuDiaryRepository : NewsDataRepository
{
    private val TAG = "ZhiHuDiaryRepository"

    private lateinit var curDataDate: DateBean

    override fun getLatestNews(): MutableList<NewsModel>
    {
        curDataDate = DateBean(System.currentTimeMillis())
        return getDiaryData("http://news-at.zhihu.com/api/4/news/latest")
    }

    override fun loadMoreNews(): MutableList<NewsModel>
    {
        curDataDate = curDataDate.lastDay()
        return getDiaryData("http://news-at.zhihu.com/api/4/news/before/${curDataDate.getDateInfo()}")
    }

    override fun getNewsDetail(diaryUrl: String): Any?
    {
        Log.d(TAG, "getDiaryDetail() called with: diaryUrl = [$diaryUrl]")

        val diaryDetailModel = HttpUtil.requestData(diaryUrl, DiaryDetailModel::class.java) ?: return null
        val doc = Jsoup.parse(diaryDetailModel.body)

        val questionTitleElements = doc.getElementsByClass("question-title")
        val authorNameElements = doc.getElementsByClass("author")
        val authorAvatarElements = doc.getElementsByClass("avatar")
        val authorBioElements = doc.getElementsByClass("bio")
        val contentElements = doc.getElementsByClass("content")

        val questionList = mutableListOf<ZhiHuQuestionModel>()

        val elementsSizeList = listOf(questionTitleElements.size, authorNameElements.size, authorAvatarElements.size, authorBioElements.size, contentElements.size)
        val minSize = elementsSizeList.minBy { it } ?: 0

        for (index in 0 until minSize)
        {
            questionList.add(ZhiHuQuestionModel(if (minSize == 1) "" else "Q：${questionTitleElements[index].text()}", contentElements[index].toString(),
                    AuthorModel(authorNameElements[index].text(), authorAvatarElements[index].attr("src"), authorBioElements[index].text())))
        }

        return ZhiHuDiaryDetailModel(diaryDetailModel.title, diaryDetailModel.images?.first(), questionList)
    }

    override fun canLoadMore() = true

    private fun getDiaryData(url: String): MutableList<NewsModel>
    {
        Log.d(TAG, "getDiaryData() called with: url = [$url]")
        val newsList = mutableListOf<NewsModel>()
        val diaryDataModel = HttpUtil.requestData(url, DiaryDataModel::class.java)
        diaryDataModel?.storyList?.forEach {
            newsList.add(NewsModel("http://news-at.zhihu.com/api/4/news/${it.id}", it.title, it.imageList.orEmpty(), AppConstant.NEWS_SOURCE_ZHIHU_DIARY))
        }
        return newsList
    }
}