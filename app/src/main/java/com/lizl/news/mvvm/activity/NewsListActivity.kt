package com.lizl.news.mvvm.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lizl.news.R
import com.lizl.news.adapter.NewsListAdapter
import com.lizl.news.databinding.ActivityNewsListBinding
import com.lizl.news.mvvm.base.BaseActivity
import com.lizl.news.mvvm.viewmodel.NewsViewModel

class NewsListActivity : BaseActivity<ActivityNewsListBinding>(R.layout.activity_news_list)
{
    private val newsListAdapter = NewsListAdapter()

    override fun initView()
    {
        val newViewModel = ViewModelProvider.AndroidViewModelFactory(this.application).create(NewsViewModel::class.java)

        dataBinding.newsListAdapter = newsListAdapter

        dataBinding.refreshLayout.setEnableRefresh(true)
        dataBinding.refreshLayout.setEnableLoadMore(true)

        dataBinding.refreshLayout.setOnRefreshListener { newViewModel.refreshNews() }
        dataBinding.refreshLayout.setOnLoadMoreListener { newViewModel.loadMoreNews() }

        newViewModel.getNewLiveData().observe(this, Observer {
            dataBinding.refreshLayout.finishRefresh()
            dataBinding.refreshLayout.finishLoadMore()
            newsListAdapter.setDiffNewData(it.toMutableList())
        })

        newViewModel.refreshNews()
    }
}