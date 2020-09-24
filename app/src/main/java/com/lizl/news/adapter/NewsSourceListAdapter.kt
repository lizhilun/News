package com.lizl.news.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.databinding.ItemNewsSourceBinding
import com.lizl.news.model.news.source.NewsSourceModel
import com.lizl.news.util.NewsUtil

class NewsSourceListAdapter(sourceList: MutableList<NewsSourceModel> = mutableListOf()) :
        BaseQuickAdapter<NewsSourceModel, BaseViewHolder>(R.layout.item_news_source, sourceList)
{
    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemNewsSourceBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: NewsSourceModel)
    {
        helper.getBinding<ItemNewsSourceBinding>()?.apply {

            newsSourceModel = item

            tvName.text = NewsUtil.getNewsSourceName(item.newSource)

            ivShow.isSelected = item.visible

            ivTop.setOnClickListener { upModel(item) }

            ivDown.setOnClickListener { downModel(item) }

            ivShow.setOnClickListener {
                item.visible = !item.visible
                ivShow.isSelected = item.visible
            }

            executePendingBindings()
        }
    }

    private fun upModel(model: NewsSourceModel)
    {
        val position = getItemPosition(model)
        if (position <= 0) return
        val upperModel = getItemOrNull(position - 1) ?: return
        model.position = position - 1
        upperModel.position = position
        data[position - 1] = model
        data[position] = upperModel
        notifyItemMoved(position, position - 1)
    }

    private fun downModel(model: NewsSourceModel)
    {
        val position = getItemPosition(model)
        if (position < 0 || position >= getDefItemCount() - 1) return
        val downModel = getItemOrNull(position + 1) ?: return
        model.position = position + 1
        downModel.position = position
        data[position + 1] = model
        data[position] = downModel
        notifyItemMoved(position, position + 1)
    }
}