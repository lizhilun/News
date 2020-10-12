package com.lizl.news.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.custom.function.scrollToTop
import com.lizl.news.databinding.ItemZhihuAnswerPagerBinding
import com.lizl.news.model.news.zhihu.ZhiHuAnswerModel

class ZhiHuAnswerPagerAdapter : BaseQuickAdapter<ZhiHuAnswerModel, BaseViewHolder>(R.layout.item_zhihu_answer_pager)
{
    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemZhihuAnswerPagerBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: ZhiHuAnswerModel)
    {
        helper.getBinding<ItemZhihuAnswerPagerBinding>()?.apply {
            answerModel = item

            svContent.scrollToTop()

            executePendingBindings()
        }
    }
}