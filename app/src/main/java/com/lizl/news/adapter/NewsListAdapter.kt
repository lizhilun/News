package com.lizl.news.adapter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.constant.AppConstant
import com.lizl.news.custom.other.CustomDiffUtil
import com.lizl.news.databinding.ItemNewsBinding
import com.lizl.news.model.NewsModel
import com.lizl.news.mvvm.activity.HeadlineDetailActivity
import com.lizl.news.mvvm.activity.ZhiHuDetailActivity
import com.lizl.news.util.ActivityUtil

class NewsListAdapter : BaseQuickAdapter<NewsModel, BaseViewHolder>(R.layout.item_news), LoadMoreModule
{

    init
    {
        setDiffCallback(CustomDiffUtil({ oldItem, newItem -> oldItem.detailUrl == newItem.detailUrl }, { oldItem, newItem -> oldItem == newItem }))
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemNewsBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: NewsModel)
    {
        helper.getBinding<ItemNewsBinding>()?.apply {
            newsModel = item

            rvImage.layoutManager = GridLayoutManager(context, 3)
            rvImage.adapter = ImageGridAdapter(item.coverImageList.toMutableList())

            helper.itemView.setOnClickListener {
                ActivityUtil.turnToActivity(when (item.platform)
                {
                    AppConstant.NEWS_PLATFORM_ZHIHU_DIARY -> ZhiHuDetailActivity::class.java
                    else                                  -> HeadlineDetailActivity::class.java
                }, item.detailUrl)
            }

            executePendingBindings()
        }
    }
}