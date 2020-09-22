package com.lizl.news.mvvm.activity

import com.blankj.utilcode.util.ToastUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lizl.news.R
import com.lizl.news.adapter.NewsSourceListAdapter
import com.lizl.news.constant.EventConstant
import com.lizl.news.databinding.ActivityNewsSourcesConfigBinding
import com.lizl.news.mvvm.base.BaseActivity
import com.lizl.news.util.NewsUtil
import kotlinx.android.synthetic.main.activity_news_sources_config.*

class NewsSourcesConfigActivity : BaseActivity<ActivityNewsSourcesConfigBinding>(R.layout.activity_news_sources_config)
{
    override fun initView()
    {
        val newsSourceListAdapter = NewsSourceListAdapter(NewsUtil.getAllNewsSource().toMutableList())
        dataBinding.newsSourceListAdapter = newsSourceListAdapter

        ctb_title.setOnBackBtnClickListener { super.onBackPressed() }

        ctb_title.setOnActionClickListener {
            NewsUtil.saveNewsSources(newsSourceListAdapter.data)
            ToastUtils.showShort(R.string.success_to_save)
            LiveEventBus.get(EventConstant.EVENT_NEWS_SOURCES_UPDATE).post(true)
        }
    }
}