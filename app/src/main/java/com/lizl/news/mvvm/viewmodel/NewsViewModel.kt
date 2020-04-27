package com.lizl.news.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lizl.news.constant.AppConstant
import com.lizl.news.model.NewsModel
import com.lizl.news.mvvm.repository.RepositoryUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel()
{
    private val newsLiveData = MutableLiveData<MutableList<NewsModel>>()
    private var hasMoreDataLiveData = MutableLiveData<Boolean>()

    private var newsDataRepository = RepositoryUtil.getRepository(AppConstant.NEWS_PLATFORM_ZHIHU_DIARY)

    fun getNewLiveData() = newsLiveData

    fun getHasMoreDataLiveData() = hasMoreDataLiveData

    fun updateNewsSource(source: String)
    {
        newsDataRepository = RepositoryUtil.getRepository(source)
        hasMoreDataLiveData.postValue(newsDataRepository.canLoadMore())
        refreshNews()
    }

    fun refreshNews()
    {
        GlobalScope.launch {
            val newsList = mutableListOf<NewsModel>()
            newsList.addAll(newsDataRepository.getLatestNews())
            newsLiveData.postValue(newsList)
        }
    }

    fun loadMoreNews()
    {
        GlobalScope.launch {
            val newsList = newsLiveData.value ?: mutableListOf()
            newsList.addAll(newsDataRepository.loadMoreNews())
            newsLiveData.postValue(newsList)
        }
    }
}