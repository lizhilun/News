package com.lizl.news.adapter

import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.databinding.ItemSettingBinding
import com.lizl.news.model.setting.BaseSettingModel
import com.lizl.news.model.setting.BooleanSettingModel
import kotlinx.android.synthetic.main.item_setting.view.*

class SettingListAdapter(operationList: MutableList<BaseSettingModel> = mutableListOf()) :
    BaseQuickAdapter<BaseSettingModel, BaseViewHolder>(R.layout.item_setting, operationList)
{
    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemSettingBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: BaseSettingModel)
    {
        helper.getBinding<ItemSettingBinding>()?.apply {

            settingModel = item

            with(helper.itemView) {

                iv_toggle.isVisible = item is BooleanSettingModel

                iv_toggle.setOnClickListener { }

                setOnClickListener { item.callback.invoke() }
            }

            executePendingBindings()
        }
    }
}