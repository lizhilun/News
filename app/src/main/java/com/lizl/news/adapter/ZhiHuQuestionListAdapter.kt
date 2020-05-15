package com.lizl.news.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.databinding.ItemZhihuQuestionBinding
import com.lizl.news.model.news.zhihu.ZhiHuQuestionModel

class ZhiHuQuestionListAdapter(questionList: List<ZhiHuQuestionModel>) :
        BaseQuickAdapter<ZhiHuQuestionModel, BaseViewHolder>(R.layout.item_zhihu_question, questionList.toMutableList())
{

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemZhihuQuestionBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: ZhiHuQuestionModel)
    {
        helper.getBinding<ItemZhihuQuestionBinding>()?.apply {
            questionModel = item
            answerAdapter = ZhiHuAnswerListAdapter(item.answerList)

            executePendingBindings()
        }
    }
}