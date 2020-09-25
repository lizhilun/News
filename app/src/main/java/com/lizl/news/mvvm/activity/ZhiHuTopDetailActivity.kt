package com.lizl.news.mvvm.activity

import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lizl.news.R
import com.lizl.news.adapter.ZhiHuAnswerPagerAdapter
import com.lizl.news.constant.AppConstant
import com.lizl.news.custom.function.registerOnPageChangeCallback
import com.lizl.news.databinding.ActivityZhihuTopDetailBinding
import com.lizl.news.mvvm.base.BaseActivity
import com.lizl.news.mvvm.viewmodel.ZhiHuTopViewModel
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_zhihu_top_detail.*

class ZhiHuTopDetailActivity : BaseActivity<ActivityZhihuTopDetailBinding>(R.layout.activity_zhihu_top_detail)
{

    private val viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(ZhiHuTopViewModel::class.java)

    override fun initView()
    {
        val detailUrl = intent?.getStringExtra(AppConstant.BUNDLE_NEWS_URL).orEmpty()

        val zhiHuAnswerPagerAdapter = ZhiHuAnswerPagerAdapter()
        vp_answer.adapter = zhiHuAnswerPagerAdapter

        vp_answer.offscreenPageLimit = 2

        viewModel.bindUrl(detailUrl)

        viewModel.getAnswerListLiveData().observe(this, Observer { zhiHuAnswerPagerAdapter.addData(it) })

        viewModel.getQuestionTitleLiveData().observe(this, Observer { tv_title.text = it })

        viewModel.getAnswerCountLiveData().observe(this, Observer { updateIndicator() })

        vp_answer.registerOnPageChangeCallback {
            updateIndicator()
            if (it >= vp_answer.size - 1) viewModel.loadMoreAnswers()
        }
    }

    private fun updateIndicator()
    {
        tv_indicator.text = "${vp_answer.currentItem + 1}/${viewModel.getAnswerCountLiveData().value ?: 0}"
    }

    override fun onDestroy()
    {
        super.onDestroy()

        RichText.recycle()
    }
}
