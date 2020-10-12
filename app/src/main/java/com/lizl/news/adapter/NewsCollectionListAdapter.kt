package com.lizl.news.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.custom.other.CustomDiffUtil
import com.lizl.news.databinding.ItemNewsCollectionBinding
import com.lizl.news.model.news.colletion.NewsCollectionModel
import com.lizl.news.util.ActivityUtil

class NewsCollectionListAdapter : BaseQuickAdapter<NewsCollectionModel, BaseViewHolder>(R.layout.item_news_collection), LoadMoreModule
{

    init
    {
        setDiffCallback(CustomDiffUtil({ oldItem, newItem -> oldItem == newItem }, { oldItem, newItem -> oldItem == newItem }))
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemNewsCollectionBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: NewsCollectionModel)
    {
        helper.getBinding<ItemNewsCollectionBinding>()?.apply {

            newsCollectionModel = item

            helper.itemView.setOnClickListener { ActivityUtil.turnToNewsDetailActivity(item.newsSource, item.newsTitle, item.newsUrl) }

            executePendingBindings()
        }
    }
}