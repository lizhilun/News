package com.lizl.news.mvvm.repository

import com.lizl.news.model.news.NewsModel

interface NewsDataRepository
{
    fun getLatestNews(): MutableList<NewsModel>

    fun loadMoreNews(): MutableList<NewsModel>

    fun getNewsDetail(dailyUrl: String): Any?

    fun canLoadMore(): Boolean

    fun getNewsRequestUrl(): String
}