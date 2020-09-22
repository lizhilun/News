package com.lizl.news.mvvm.activity

import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.lizl.news.R
import com.lizl.news.adapter.FragmentPagerAdapter
import com.lizl.news.custom.view.MenuDrawLayout
import com.lizl.news.databinding.ActivityNewsListBinding
import com.lizl.news.mvvm.base.BaseActivity
import com.lizl.news.mvvm.fragment.NewsListFragment
import com.lizl.news.util.NewsUtil
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupPosition
import kotlinx.android.synthetic.main.activity_news_list.*

class NewsListActivity : BaseActivity<ActivityNewsListBinding>(R.layout.activity_news_list)
{

    private val menuDrawLayout: MenuDrawLayout by lazy { MenuDrawLayout(this) }

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

        iv_menu.setOnClickListener {
            XPopup.Builder(this)
                .popupPosition(PopupPosition.Left)//右边
                .hasStatusBarShadow(false)
                .asCustom(menuDrawLayout)
                .show();
        }
    }
}