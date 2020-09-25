package com.lizl.news.mvvm.activity

import androidx.lifecycle.Observer
import com.lizl.news.R
import com.lizl.news.adapter.NewsCollectionListAdapter
import com.lizl.news.dao.AppDatabase
import com.lizl.news.databinding.ActivityNewsCollectionBinding
import com.lizl.news.mvvm.base.BaseActivity

class NewsCollectionActivity : BaseActivity<ActivityNewsCollectionBinding>(R.layout.activity_news_collection)
{
    private val newsCollectionListAdapter = NewsCollectionListAdapter()

    override fun initView()
    {
        dataBinding.newsCollectionListAdapter = newsCollectionListAdapter
    }

    override fun initData()
    {
        AppDatabase.instance.getNewsCollectionDao().getAllNewConnectionLiveData().observe(this, Observer {
            newsCollectionListAdapter.setDiffNewData(it)
        })
    }
}