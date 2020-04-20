package com.lizl.news.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lizl.news.model.NewsModel
import com.lizl.news.mvvm.repository.ZhiHuDiaryRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel()
{
    private val TAG = "NewsViewModel"

    private val newsLiveData = MutableLiveData<MutableList<NewsModel>>()

    fun getNewLiveData() = newsLiveData

    fun refreshNews()
    {
        GlobalScope.launch {
            newsLiveData.value?.clear()
            newsLiveData.postValue(ZhiHuDiaryRepository.getLatestDiaryData())
        }
    }

    fun loadMoreNews()
    {
        GlobalScope.launch {
            val newsList = newsLiveData.value ?: mutableListOf()
            newsList.addAll(ZhiHuDiaryRepository.getBeforeDiaryData())
            newsLiveData.postValue(newsList)
        }
    }
}