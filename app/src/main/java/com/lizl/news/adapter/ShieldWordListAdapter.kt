package com.lizl.news.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.custom.other.CustomDiffUtil
import com.lizl.news.databinding.ItemShieldWordBinding
import com.lizl.news.model.news.shieldword.ShieldWordModel

class ShieldWordListAdapter : BaseQuickAdapter<ShieldWordModel, BaseViewHolder>(R.layout.item_shield_word)
{
    init
    {
        setDiffCallback(CustomDiffUtil({ oldItem, newItem -> oldItem.word == newItem.word }, { oldItem, newItem -> oldItem == newItem }))
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemShieldWordBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: ShieldWordModel)
    {
        helper.getBinding<ItemShieldWordBinding>()?.apply {

            shieldWordModel = item

            executePendingBindings()
        }
    }
}