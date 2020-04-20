package com.lizl.news.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.databinding.ItemImagePagerBinding

class ImagePagerAdapter(imageList: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_image_pager, imageList)
{
    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemImagePagerBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: String)
    {
        helper.getBinding<ItemImagePagerBinding>()?.apply {
            imageUrl = item
            executePendingBindings()
        }
    }
}