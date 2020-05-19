package com.lizl.news.mvvm.activity

import com.lizl.news.R
import com.lizl.news.adapter.ZhiHuQuestionListAdapter
import com.lizl.news.constant.AppConstant
import com.lizl.news.databinding.ActivityZhihuDailyDetailBinding
import com.lizl.news.model.news.zhihu.daily.ZhiHuDailyDetailModel
import com.lizl.news.mvvm.base.BaseActivity
import com.lizl.news.mvvm.repository.RepositoryManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ZhiHuDetailActivity : BaseActivity<ActivityZhihuDailyDetailBinding>(R.layout.activity_zhihu_daily_detail)
{
    override fun initView()
    {
        val detailUrl = intent?.getStringExtra(AppConstant.BUNDLE_DATA_STRING).orEmpty()
        GlobalScope.launch {
            val dailyDetailModel = RepositoryManager.getRepository(AppConstant.NEWS_SOURCE_ZHIHU_DAILY).getNewsDetail(detailUrl)
            GlobalScope.launch(Dispatchers.Main) {
                if (dailyDetailModel is ZhiHuDailyDetailModel)
                {
                    dataBinding.detailModel = dailyDetailModel
                    dataBinding.questionAdapter = ZhiHuQuestionListAdapter(dailyDetailModel.questionList)
                }
            }
        }
    }
}