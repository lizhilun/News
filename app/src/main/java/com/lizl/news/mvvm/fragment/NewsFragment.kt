package com.lizl.news.mvvm.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lizl.news.R
import com.lizl.news.adapter.NewsListAdapter
import com.lizl.news.custom.recylerviewitemdivider.ListDividerItemDecoration
import com.lizl.news.databinding.FragmentNewsListBinding
import com.lizl.news.mvvm.base.BaseFragment
import com.lizl.news.mvvm.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news_list.*

class NewsFragment(private val newsSource: String) : BaseFragment<FragmentNewsListBinding>(R.layout.fragment_news_list)
{
    private val newsListAdapter = NewsListAdapter()

    private lateinit var newsViewModel: NewsViewModel

    override fun initView()
    {
        dataBinding.newsListAdapter = newsListAdapter

        dataBinding.refreshLayout.let {
            it.setEnableRefresh(true)
            it.setEnableLoadMore(false)

            it.setOnRefreshListener { newsViewModel.refreshNews() }
        }

        newsViewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(NewsViewModel::class.java)

        newsListAdapter.loadMoreModule?.let {
            it.isEnableLoadMore = true
            it.preLoadNumber = 5

            it.setOnLoadMoreListener { newsViewModel.loadMoreNews() }
        }

        rv_news_list.addItemDecoration(ListDividerItemDecoration(resources.getDimensionPixelSize(R.dimen.global_content_padding_content)))

        newsViewModel.getNewLiveData().observe(this, Observer {
            dataBinding.refreshLayout.finishRefresh()
            newsListAdapter.loadMoreModule?.loadMoreComplete()
            newsListAdapter.setDiffNewData(it.toMutableList())
        })

        newsViewModel.getHasMoreDataLiveData().observe(this, Observer { newsListAdapter.loadMoreModule?.isEnableLoadMore = it })

        newsViewModel.setNewsSource(newsSource)
    }
}