package com.lizl.news.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.databinding.ItemZhihuAnswerBinding
import com.lizl.news.model.zhihu.ZhiHuQuestionModel

class ZhiHuAnswerPagerAdapter(questionList: List<ZhiHuQuestionModel>) :
        BaseQuickAdapter<ZhiHuQuestionModel, BaseViewHolder>(R.layout.item_zhihu_answer, questionList.toMutableList())
{

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemZhihuAnswerBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: ZhiHuQuestionModel)
    {
        helper.getBinding<ItemZhihuAnswerBinding>()?.apply {
            questionModel = item
            tvTitle.paint.isFakeBoldText = true
            executePendingBindings()
        }
    }
}