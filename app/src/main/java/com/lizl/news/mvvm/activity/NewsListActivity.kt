package com.lizl.news.mvvm.activity

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.lizl.news.R
import com.lizl.news.adapter.FragmentPagerAdapter
import com.lizl.news.constant.AppConstant
import com.lizl.news.databinding.ActivityNewsListBinding
import com.lizl.news.model.OperationItem
import com.lizl.news.mvvm.base.BaseActivity
import com.lizl.news.mvvm.fragment.NewsListFragment
import com.lizl.news.util.NewsUtil
import com.lizl.news.util.PopupUtil
import kotlinx.android.synthetic.main.activity_news_list.*

class NewsListActivity : BaseActivity<ActivityNewsListBinding>(R.layout.activity_news_list)
{
    private lateinit var fragmentPagersAdapter: FragmentPagerAdapter
    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun initView()
    {
        fragmentPagersAdapter = FragmentPagerAdapter(this)
        vp_page.adapter = fragmentPagersAdapter

        updateNewsPlatform(AppConstant.NEWS_PLATFORM_ZHIHU)

        tv_title.setOnClickListener {
            PopupUtil.showBindViewOperationListPopup(it, mutableListOf<OperationItem>().apply {
                NewsUtil.getNewsAllPlatform().forEach { newsPlatform ->
                    add(OperationItem(NewsUtil.getNewsPlatformName(newsPlatform)) { updateNewsPlatform(newsPlatform) })
                }
            })
        }
    }

    private fun updateNewsPlatform(newsPlatform: String)
    {
        tv_title.text = NewsUtil.getNewsPlatformName(newsPlatform)
        val newsSources = NewsUtil.getNewsSourceByPlatform(newsPlatform)
        tl_source_title.isVisible = newsSources.size > 1
        tabLayoutMediator?.detach()
        tabLayoutMediator = TabLayoutMediator(tl_source_title, vp_page, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            if (position >= newsSources.size) return@TabConfigurationStrategy
            tab.text = NewsUtil.getNewsSourceName(newsSources[position])
        })
        tabLayoutMediator?.attach()
        val fragmentList = mutableListOf<Fragment>().apply { newsSources.forEach { add(NewsListFragment(it)) } }
        fragmentPagersAdapter.setFragmentList(fragmentList)
        vp_page.offscreenPageLimit = newsSources.size
        vp_page.setCurrentItem(0, false)
    }
}