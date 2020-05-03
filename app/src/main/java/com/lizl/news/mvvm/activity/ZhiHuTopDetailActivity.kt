package com.lizl.news.mvvm.activity

import android.graphics.drawable.GradientDrawable
import androidx.viewpager2.widget.ViewPager2
import com.lizl.news.R
import com.lizl.news.adapter.ZhiHuAnswerPagerAdapter
import com.lizl.news.adapter.ZhiHuQuestionListAdapter
import com.lizl.news.constant.AppConstant
import com.lizl.news.custom.function.deleteStr
import com.lizl.news.databinding.ActivityZhihuTopDetailBinding
import com.lizl.news.model.AuthorModel
import com.lizl.news.model.zhihu.ZhiHuAnswersResponseModel
import com.lizl.news.model.zhihu.ZhiHuQuestionModel
import com.lizl.news.mvvm.base.BaseActivity
import com.lizl.news.mvvm.repository.RepositoryManager
import kotlinx.android.synthetic.main.activity_zhihu_top_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ZhiHuTopDetailActivity : BaseActivity<ActivityZhihuTopDetailBinding>(R.layout.activity_zhihu_top_detail)
{
    override fun initView()
    {
        val detailUrl = intent?.getStringExtra(AppConstant.BUNDLE_DATA_STRING).orEmpty()
        GlobalScope.launch {
            val zhiHuAnswersResponseModel = RepositoryManager.getRepository(AppConstant.NEWS_SOURCE_ZHIHU_TOP).getNewsDetail(detailUrl)
            GlobalScope.launch(Dispatchers.Main) {
                if (zhiHuAnswersResponseModel is ZhiHuAnswersResponseModel)
                {
                    tl_title.title = zhiHuAnswersResponseModel.dataList?.first()?.question?.title

                    val questionList = mutableListOf<ZhiHuQuestionModel>()
                    zhiHuAnswersResponseModel.dataList?.forEach {
                        it.author ?: return@forEach
                        questionList.add(ZhiHuQuestionModel("", it.content, AuthorModel(it.author.name, it.author.avatar_url, it.author.headline)))
                    }

                    val zhiHuAnswerPagerAdapter = ZhiHuAnswerPagerAdapter(questionList)
                    vp_answer.adapter = zhiHuAnswerPagerAdapter

                    vp_answer.offscreenPageLimit = 2
                }
            }
        }
    }
}