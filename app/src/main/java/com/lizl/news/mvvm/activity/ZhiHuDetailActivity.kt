package com.lizl.news.mvvm.activity

import com.lizl.mydiary.constant.AppConstant
import com.lizl.news.R
import com.lizl.news.adapter.ZhiHuQuestionListAdapter
import com.lizl.news.databinding.ActivityZhihuDiaryDetailBinding
import com.lizl.news.mvvm.base.BaseActivity
import com.lizl.news.mvvm.repository.ZhiHuDiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ZhiHuDetailActivity : BaseActivity<ActivityZhihuDiaryDetailBinding>(R.layout.activity_zhihu_diary_detail)
{
    override fun initView()
    {
        val detailUrl = intent?.getStringExtra(AppConstant.BUNDLE_DATA_STRING).orEmpty()
        GlobalScope.launch {
            val diaryDetailModel = ZhiHuDiaryRepository.getDiaryDetail(detailUrl)
            GlobalScope.launch(Dispatchers.Main) {
                dataBinding.detailModel = diaryDetailModel
                dataBinding.questionAdapter = ZhiHuQuestionListAdapter(diaryDetailModel?.questionList ?: emptyList())
            }
        }
    }
}