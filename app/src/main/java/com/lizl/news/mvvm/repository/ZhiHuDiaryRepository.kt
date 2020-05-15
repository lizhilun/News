package com.lizl.news.mvvm.repository

import android.util.Log
import com.lizl.news.constant.AppConstant
import com.lizl.news.model.other.DateBean
import com.lizl.news.model.news.NewsModel
import com.lizl.news.model.news.zhihu.*
import com.lizl.news.model.news.zhihu.diary.ZhiHuDiaryDataModel
import com.lizl.news.model.news.zhihu.diary.ZhiHuDiaryDetailModel
import com.lizl.news.model.news.zhihu.diary.ZhiHuDiaryDetailResponseModel
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

        val diaryDetailModel = HttpUtil.requestData(diaryUrl, ZhiHuDiaryDetailResponseModel::class.java) ?: return null
        val doc = Jsoup.parse(diaryDetailModel.body)

        val questionList = mutableListOf<ZhiHuQuestionModel>()

        doc.getElementsByClass("question").forEach { questionElement ->
            val questionTitle = questionElement.getElementsByClass("question-title")?.first()?.text().orEmpty()
            val answerList = mutableListOf<ZhiHuAnswerModel>()
            questionElement.getElementsByClass("answer").forEach answerForEach@{ answerElement ->
                val authorName = answerElement.getElementsByClass("author")?.first()?.text() ?: return@answerForEach
                val authorAvatar = answerElement.getElementsByClass("avatar")?.first()?.attr("src").orEmpty()
                val authorBio = answerElement.getElementsByClass("bio")?.first()?.text().orEmpty()
                val content = answerElement.getElementsByClass("content")?.first()?.toString() ?: return@answerForEach
                answerList.add(ZhiHuAnswerModel(content, ZhiHuAuthorModel(authorName, authorAvatar, authorBio)))
            }
            questionList.add(ZhiHuQuestionModel(questionTitle, answerList))
        }

        return ZhiHuDiaryDetailModel(diaryDetailModel.title, diaryDetailModel.images?.first(),
                questionList)
    }

    override fun canLoadMore() = true

    private fun getDiaryData(url: String): MutableList<NewsModel>
    {
        Log.d(TAG, "getDiaryData() called with: url = [$url]")
        val newsList = mutableListOf<NewsModel>()
        val diaryDataModel = HttpUtil.requestData(url, ZhiHuDiaryDataModel::class.java)
        diaryDataModel?.storyList?.forEach {
            newsList.add(NewsModel("http://news-at.zhihu.com/api/4/news/${it.id}", it.title, it.imageList.orEmpty(), AppConstant.NEWS_SOURCE_ZHIHU_DIARY))
        }
        return newsList
    }
}