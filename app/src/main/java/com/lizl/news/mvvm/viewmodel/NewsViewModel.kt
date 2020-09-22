package com.lizl.news.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lizl.news.model.news.NewsModel
import com.lizl.news.mvvm.repository.NewsDataRepository
import com.lizl.news.mvvm.repository.RepositoryManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel()
{
    val newsLiveData = MutableLiveData<MutableList<NewsModel>>()
    var hasMoreDataLiveData = MutableLiveData<Boolean>()
    val newsRequestFailedLiveData = MutableLiveData<String>()

    private lateinit var newsDataRepository: NewsDataRepository

    fun setNewsSource(source: String)
    {
        newsDataRepository = RepositoryManager.getRepository(source)
        hasMoreDataLiveData.postValue(newsDataRepository.canLoadMore())
        refreshNews()
    }

    fun refreshNews()
    {
        GlobalScope.launch {
            val newsList = mutableListOf<NewsModel>()
            newsList.addAll(newsDataRepository.getLatestNews())
            newsLiveData.postValue(newsList)
            if (newsList.isEmpty())
            {
                newsRequestFailedLiveData.postValue(newsDataRepository.getNewsRequestUrl())
            }
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