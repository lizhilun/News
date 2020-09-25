package com.lizl.news.adapter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.custom.other.CustomDiffUtil
import com.lizl.news.databinding.ItemNewsBinding
import com.lizl.news.model.news.NewsModel
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

            if (item.coverImageList.size > 1)
            {
                rvImage.layoutManager = GridLayoutManager(context, 3)
                rvImage.adapter = ImageGridAdapter(item.coverImageList.toMutableList())
            }

            helper.itemView.setOnClickListener { ActivityUtil.turnToNewsDetailActivity(item.source, item.title, item.detailUrl) }

            executePendingBindings()
        }
    }
}