package com.lizl.news.mvvm.viewmodel

import android.util.Log
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
        ZhiHuDiaryRepository.getDiaryData { result, list ->
            newsLiveData.value?.clear()
            newsLiveData.postValue(list.toMutableList())
        }
    }
}