package com.lizl.news.mvvm.activity

import androidx.lifecycle.Observer
import com.lizl.news.R
import com.lizl.news.adapter.ZhiHuQuestionListAdapter
import com.lizl.news.constant.AppConstant
import com.lizl.news.dao.AppDatabase
import com.lizl.news.databinding.ActivityZhihuDailyDetailBinding
import com.lizl.news.model.news.colletion.NewsCollectionModel
import com.lizl.news.model.news.zhihu.daily.ZhiHuDailyDetailModel
import com.lizl.news.mvvm.base.BaseActivity
import com.lizl.news.mvvm.repository.RepositoryManager
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ZhiHuDetailActivity : BaseActivity<ActivityZhihuDailyDetailBinding>(R.layout.activity_zhihu_daily_detail)
{
    override fun initView()
    {
        val detailUrl = intent?.getStringExtra(AppConstant.BUNDLE_NEWS_URL).orEmpty()
        val newsTitle = intent?.getStringExtra(AppConstant.BUNDLE_NEWS_TITLE)
        val newsSource = AppConstant.NEWS_SOURCE_ZHIHU_DAILY

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

        var collectionModel: NewsCollectionModel? = null

        AppDatabase.instance.getNewsCollectionDao().findCollection(newsTitle.orEmpty(), detailUrl.orEmpty()).observe(this, Observer {
            collectionModel = it
            fab_collect.isSelected = it != null
        })

        fab_collect.setOnClickListener {
            if (collectionModel != null)
            {
                AppDatabase.instance.getNewsCollectionDao().delete(collectionModel!!)
            }
            else
            {
                collectionModel = NewsCollectionModel(newsSource = newsSource, newsTitle = newsTitle.orEmpty(), newsUrl = detailUrl,
                        collectionTime = System.currentTimeMillis()).apply {
                    AppDatabase.instance.getNewsCollectionDao().insert(this)
                }
            }
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()

        RichText.recycle()
    }
}