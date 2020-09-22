package com.lizl.news.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.databinding.ItemOperationBinding
import com.lizl.news.model.other.OperationItem

class OperationListAdapter(operationList: MutableList<OperationItem> = mutableListOf()) :
        BaseQuickAdapter<OperationItem, BaseViewHolder>(R.layout.item_operation, operationList)
{
    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemOperationBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: OperationItem)
    {
        helper.getBinding<ItemOperationBinding>()?.apply {

            operationModel = item

            helper.itemView.setOnClickListener { item.operationItemCallBack.invoke() }

            executePendingBindings()
        }
    }
}