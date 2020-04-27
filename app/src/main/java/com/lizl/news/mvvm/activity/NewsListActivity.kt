package com.lizl.news.mvvm.activity

import android.media.VolumeShaper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lizl.news.R
import com.lizl.news.adapter.NewsListAdapter
import com.lizl.news.constant.AppConstant
import com.lizl.news.custom.function.getTitleTextView
import com.lizl.news.databinding.ActivityNewsListBinding
import com.lizl.news.model.NewsModel
import com.lizl.news.model.OperationItem
import com.lizl.news.mvvm.base.BaseActivity
import com.lizl.news.mvvm.viewmodel.NewsViewModel
import com.lizl.news.util.NewsUtil
import com.lizl.news.util.PopupUtil
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnSelectListener
import kotlinx.android.synthetic.main.activity_news_list.*

class NewsListActivity : BaseActivity<ActivityNewsListBinding>(R.layout.activity_news_list)
{
    private val newsListAdapter = NewsListAdapter()

    private val newViewModel = ViewModelProvider.AndroidViewModelFactory(this.application).create(NewsViewModel::class.java)

    override fun initView()
    {
        dataBinding.newsListAdapter = newsListAdapter

        dataBinding.refreshLayout.let {
            it.setEnableRefresh(true)
            it.setEnableLoadMore(false)

            it.setOnRefreshListener { newViewModel.refreshNews() }
        }

        newsListAdapter.loadMoreModule?.let {
            it.isEnableLoadMore = true
            it.preLoadNumber = 5

            it.setOnLoadMoreListener { newViewModel.loadMoreNews() }
        }

        newViewModel.getNewLiveData().observe(this, Observer {
            dataBinding.refreshLayout.finishRefresh()
            newsListAdapter.loadMoreModule?.loadMoreComplete()
            newsListAdapter.setDiffNewData(it.toMutableList())
        })

        updateNewsSource(AppConstant.NEWS_PLATFORM_ZHIHU_DIARY)

        tl_title.getTitleTextView()?.setOnClickListener {
            PopupUtil.showBindViewOperationListPopup(it, mutableListOf<OperationItem>().apply {
                NewsUtil.getNewsAllSources().forEach { newsSource ->
                    add(OperationItem(NewsUtil.getNewsSourceName(newsSource)) { updateNewsSource(newsSource) })
                }
            })
        }
    }

    private fun updateNewsSource(newsSource: String)
    {
        newsListAdapter.setNewData(mutableListOf())
        tl_title.title = NewsUtil.getNewsSourceName(newsSource)
        newViewModel.updateNewsSource(newsSource)
    }
}