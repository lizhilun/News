package com.lizl.news.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lizl.news.model.NewsModel
import com.lizl.news.mvvm.repository.ZhiHuDiaryRepository

class NewsViewModel : ViewModel()
{
    private val TAG = "NewsViewModel"

    private val newsLiveData = MutableLiveData<MutableList<NewsModel>>()

    fun getNewLiveData() = newsLiveData

    fun refreshNews()
    {
        ZhiHuDiaryRepository.getLatestDiaryData { result, list ->
            newsLiveData.value?.clear()
            newsLiveData.postValue(list.toMutableList())
        }
    }

    fun loadMoreNews()
    {
        ZhiHuDiaryRepository.getBeforeDiaryData() { result, list ->
            val newsList = newsLiveData.value ?: mutableListOf()
            newsList.addAll(list)
            newsLiveData.postValue(newsList)
        }
    }
}