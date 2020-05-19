package com.lizl.news.mvvm.repository

import android.util.Log
import com.lizl.news.constant.AppConstant
import com.lizl.news.model.news.NewsModel
import com.lizl.news.model.news.zhihu.ZhiHuAnswerModel
import com.lizl.news.model.news.zhihu.ZhiHuAuthorModel
import com.lizl.news.model.news.zhihu.ZhiHuQuestionModel
import com.lizl.news.model.news.zhihu.daily.ZhiHuDailyDataModel
import com.lizl.news.model.news.zhihu.daily.ZhiHuDailyDetailModel
import com.lizl.news.model.news.zhihu.daily.ZhiHuDailyDetailResponseModel
import com.lizl.news.model.other.DateBean
import com.lizl.news.util.HttpUtil
import org.jsoup.Jsoup

class ZhiHuDailyRepository : NewsDataRepository
{
    private val TAG = "ZhiHudailyRepository"

    private lateinit var curDataDate: DateBean

    override fun getLatestNews(): MutableList<NewsModel>
    {
        curDataDate = DateBean(System.currentTimeMillis())
        return getDailyData("http://news-at.zhihu.com/api/4/news/latest")
    }

    override fun loadMoreNews(): MutableList<NewsModel>
    {
        curDataDate = curDataDate.lastDay()
        return getDailyData("http://news-at.zhihu.com/api/4/news/before/${curDataDate.getDateInfo()}")
    }

    override fun getNewsDetail(dailyUrl: String): Any?
    {
        Log.d(TAG, "getDailyDetail() called with: dailyUrl = [$dailyUrl]")

        val dailyDetailModel = HttpUtil.requestData(dailyUrl, ZhiHuDailyDetailResponseModel::class.java) ?: return null
        val doc = Jsoup.parse(dailyDetailModel.body)

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

        return ZhiHuDailyDetailModel(dailyDetailModel.title, dailyDetailModel.images?.first(), questionList)
    }

    override fun canLoadMore() = true

    private fun getDailyData(url: String): MutableList<NewsModel>
    {
        Log.d(TAG, "getDailyData() called with: url = [$url]")
        val newsList = mutableListOf<NewsModel>()
        val dailyDataModel = HttpUtil.requestData(url, ZhiHuDailyDataModel::class.java)
        dailyDataModel?.storyList?.forEach {
            newsList.add(NewsModel("http://news-at.zhihu.com/api/4/news/${it.id}", it.title, it.imageList.orEmpty(), AppConstant.NEWS_SOURCE_ZHIHU_DAILY))
        }
        return newsList
    }
}