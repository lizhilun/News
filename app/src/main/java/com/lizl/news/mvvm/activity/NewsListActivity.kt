package com.lizl.news.mvvm.activity

import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.lizl.news.R
import com.lizl.news.adapter.FragmentPagerAdapter
import com.lizl.news.databinding.ActivityNewsListBinding
import com.lizl.news.mvvm.base.BaseActivity
import com.lizl.news.mvvm.fragment.NewsListFragment
import com.lizl.news.util.NewsUtil
import kotlinx.android.synthetic.main.activity_news_list.*

class NewsListActivity : BaseActivity<ActivityNewsListBinding>(R.layout.activity_news_list)
{
    override fun initView()
    {
        val fragmentPagersAdapter = FragmentPagerAdapter(this)
        vp_page.adapter = fragmentPagersAdapter

        val newsSources = NewsUtil.getAllNewsSource()
        val tabLayoutMediator = TabLayoutMediator(tl_source_title, vp_page, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            if (position >= newsSources.size) return@TabConfigurationStrategy
            tab.text = NewsUtil.getNewsSourceName(newsSources[position])
        })
        tabLayoutMediator.attach()
        val fragmentList = mutableListOf<Fragment>().apply { newsSources.forEach { add(NewsListFragment(it)) } }
        fragmentPagersAdapter.setFragmentList(fragmentList)
        vp_page.offscreenPageLimit = 2
        vp_page.setCurrentItem(0, false)
    }
}