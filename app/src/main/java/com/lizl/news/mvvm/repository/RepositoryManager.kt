package com.lizl.news.mvvm.repository

import com.lizl.news.constant.AppConstant

object RepositoryManager
{
    private val repositoryMap = HashMap<String, NewsDataRepository>()

    fun getRepository(newsSource: String): NewsDataRepository
    {
        var repository = repositoryMap[newsSource]
        if (repository == null)
        {
            repository = when (newsSource)
            {
                AppConstant.NEWS_SOURCE_ZHIHU_DIARY      -> ZhiHuDiaryRepository()
                AppConstant.NEWS_SOURCE_ZHIHU_TOP        -> ZhiHuTopRepository()
                AppConstant.NEWS_SOURCE_TODAY_IN_HISTORY -> TodayInHistoryRepository()
                else                                     -> HeadlineRepository(newsSource)
            }
            repositoryMap[newsSource] = repository
        }
        return repository
    }
}