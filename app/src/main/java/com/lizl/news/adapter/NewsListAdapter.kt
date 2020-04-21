package com.lizl.news.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.custom.other.CustomDiffUtil
import com.lizl.news.databinding.ItemNewsBinding
import com.lizl.news.model.NewsModel
import com.lizl.news.mvvm.activity.NewsDetailActivity
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
            executePendingBindings()

            helper.itemView.setOnClickListener { ActivityUtil.turnToActivity(NewsDetailActivity::class.java, item.detailUrl) }
        }
    }
}