package com.lizl.news.mvvm.repository

import com.lizl.news.model.NewsModel

interface NewsDataRepository
{
    fun getLatestNews(): MutableList<NewsModel>

    fun loadMoreNews(): MutableList<NewsModel>

    fun getNewsDetail(diaryUrl: String): Any?
}