package com.lizl.news.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.custom.other.CustomDiffUtil
import com.lizl.news.databinding.ItemNewsBinding
import com.lizl.news.model.NewsModel

class NewsListAdapter : BaseQuickAdapter<NewsModel, BaseViewHolder>(R.layout.item_news)
{

    init
    {
        setDiffCallback(CustomDiffUtil({ oldItem, newItem -> oldItem.id == newItem.id }, { oldItem, newItem -> oldItem == newItem }))
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
        }
    }
}