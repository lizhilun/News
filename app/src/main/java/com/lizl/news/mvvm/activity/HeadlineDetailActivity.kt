package com.lizl.news.mvvm.activity

import com.lizl.news.R
import com.lizl.news.constant.AppConstant
import com.lizl.news.databinding.ActivityHeadlineDetailBinding
import com.lizl.news.mvvm.base.BaseActivity
import com.lizl.news.mvvm.repository.RepositoryUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HeadlineDetailActivity : BaseActivity<ActivityHeadlineDetailBinding>(R.layout.activity_headline_detail)
{
    override fun initView()
    {
        val detailUrl = intent?.getStringExtra(AppConstant.BUNDLE_DATA_STRING).orEmpty()
        GlobalScope.launch {
            val content = RepositoryUtil.getRepository(AppConstant.NEWS_PLATFORM_HEADLINE).getNewsDetail(detailUrl)
            GlobalScope.launch(Dispatchers.Main) {
                if (content is String)
                {
                    dataBinding.content = content
                }
            }
        }
    }
}