package com.lizl.news.mvvm.repository

import com.lizl.news.constant.AppConstant
import com.lizl.news.model.news.NewsModel
import org.jsoup.Jsoup

class DailyHotRepository(private val newsSource: String) : NewsDataRepository
{
    override fun getLatestNews(): MutableList<NewsModel>
    {
        val newsList = mutableListOf<NewsModel>()
        val doc = Jsoup.connect(getRequestUrlBySource(newsSource)).get()
        doc.getElementsByClass("al").forEach {
            val newsTitle = it.text()
            val newsUrl = it.getElementsByTag("a").attr("href")
            newsList.add(NewsModel(newsUrl, newsTitle, null, AppConstant.NEWS_SOURCE_DAILY_36_KR))
        }
        return newsList
    }

    override fun loadMoreNews() = mutableListOf<NewsModel>()

    override fun getNewsDetail(dailyUrl: String) = ""

    override fun canLoadMore() = false

    private fun getRequestUrlBySource(newsSource: String): String
    {
        return when (newsSource)
        {
            AppConstant.NEWS_SOURCE_DAILY_36_KR     -> "https://tophub.today/n/Q1Vd5Ko85R"
            AppConstant.NEWS_SOURCE_DAILY_IT_HOME   -> "https://tophub.today/n/74Kvx59dkx"
            AppConstant.NEWS_SOURCE_DAILY_SS_PAI    -> "https://tophub.today/n/Y2KeDGQdNP"
            AppConstant.NEWS_SOURCE_DAILY_HU_XIU    -> "https://tophub.today/n/5VaobgvAj1"
            AppConstant.NEWS_SOURCE_DAILY_JIAN_DAN  -> "https://tophub.today/n/NRrvWq3e5z"
            AppConstant.NEWS_SOURCE_DAILY_HU_PU     -> "https://tophub.today/n/G47o8weMmN"
            AppConstant.NEWS_SOURCE_DAILY_THE_PAPER -> "https://tophub.today/n/wWmoO5Rd4E"
            AppConstant.NEWS_SOURCE_DAILY_WEIBO     -> "https://tophub.today/n/KqndgxeLl9"
            else                                    -> ""
        }
    }
}