package com.lizl.news.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.databinding.ItemZhihuAnswerBinding
import com.lizl.news.model.news.zhihu.ZhiHuAnswerModel

class ZhiHuAnswerListAdapter(answerList: MutableList<ZhiHuAnswerModel>) :
        BaseQuickAdapter<ZhiHuAnswerModel, BaseViewHolder>(R.layout.item_zhihu_answer, answerList)
{
    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemZhihuAnswerBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: ZhiHuAnswerModel)
    {
        helper.getBinding<ItemZhihuAnswerBinding>()?.apply {
            answerModel = item
            executePendingBindings()
        }
    }
}