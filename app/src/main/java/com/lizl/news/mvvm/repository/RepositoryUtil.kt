package com.lizl.news.mvvm.repository

import com.lizl.news.constant.AppConstant

object RepositoryUtil
{
    private val repositoryMap = HashMap<String, NewsDataRepository>()

    fun getRepository(platform: String): NewsDataRepository
    {
        var repository = repositoryMap[platform]
        if (repository == null)
        {
            repository = when (platform)
            {
                AppConstant.NEWS_PLATFORM_HEADLINE    -> HeadlineRepository()
                AppConstant.NEWS_PLATFORM_ZHIHU_DIARY -> ZhiHuDiaryRepository()
                else                                  -> HeadlineRepository()
            }
            repositoryMap[platform] = repository
        }
        return repository
    }
}