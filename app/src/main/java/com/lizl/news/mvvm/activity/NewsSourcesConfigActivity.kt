package com.lizl.news.mvvm.activity

import com.blankj.utilcode.util.ToastUtils
import com.lizl.news.R
import com.lizl.news.adapter.NewsSourceListAdapter
import com.lizl.news.dao.AppDatabase
import com.lizl.news.databinding.ActivityNewsSourcesConfigBinding
import com.lizl.news.mvvm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_news_sources_config.*

class NewsSourcesConfigActivity : BaseActivity<ActivityNewsSourcesConfigBinding>(R.layout.activity_news_sources_config)
{
    override fun initView()
    {
        val newsSourceListAdapter = NewsSourceListAdapter(AppDatabase.instance.getNewsSourceDao().getAllNewsSource())
        dataBinding.newsSourceListAdapter = newsSourceListAdapter

        ctb_title.setOnBackBtnClickListener { super.onBackPressed() }

        ctb_title.setOnActionClickListener {
            AppDatabase.instance.getNewsSourceDao().updateList(newsSourceListAdapter.data)
            ToastUtils.showShort(R.string.success_to_save)
        }
    }
}