package com.lizl.news.adapter

import android.view.View
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.model.setting.*
import com.lizl.news.util.PopupUtil
import kotlinx.android.synthetic.main.item_setting_boolean.view.*
import kotlinx.android.synthetic.main.item_setting_normal.view.*
import kotlinx.android.synthetic.main.item_setting_value.view.*

class SettingListAdapter(settingList: MutableList<BaseSettingModel>) : BaseDelegateMultiAdapter<BaseSettingModel, SettingListAdapter.ViewHolder>(settingList)
{

    companion object
    {
        private const val ITEM_TYPE_DIVIDE = 1
        private const val ITEM_TYPE_NORMAL = 2
        private const val ITEM_TYPE_BOOLEAN = 3
        private const val ITEM_TYPE_STRING_RADIO = 4
    }

    init
    {
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<BaseSettingModel>()
        {
            override fun getItemType(data: List<BaseSettingModel>, position: Int): Int
            {
                return when (data[position])
                {
                    is BooleanSettingModel     -> ITEM_TYPE_BOOLEAN
                    is NormalSettingModel      -> ITEM_TYPE_NORMAL
                    is StringRadioSettingModel -> ITEM_TYPE_STRING_RADIO
                    else                       -> ITEM_TYPE_DIVIDE
                }
            }
        })

        getMultiTypeDelegate()?.let {
            it.addItemType(ITEM_TYPE_DIVIDE, R.layout.item_setting_divide)
            it.addItemType(ITEM_TYPE_BOOLEAN, R.layout.item_setting_boolean)
            it.addItemType(ITEM_TYPE_NORMAL, R.layout.item_setting_normal)
            it.addItemType(ITEM_TYPE_STRING_RADIO, R.layout.item_setting_value)
        }
    }

    override fun convert(helper: ViewHolder, item: BaseSettingModel)
    {
        when (item)
        {
            is BooleanSettingModel     -> helper.bindBooleanViewHolder(item)
            is NormalSettingModel      -> helper.bindNormalViewHolder(item)
            is StringRadioSettingModel -> helper.bindRadioViewHolder(item)
        }
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView)
    {
        fun bindBooleanViewHolder(settingModel: BooleanSettingModel)
        {
            with(itemView) {

                settingModel.icon?.let {
                    iv_boolean_icon.setImageResource(it)
                }

                tv_boolean_setting_name.text = settingModel.name
                iv_boolean_setting_checked.isSelected = settingModel.getValue()

                setOnClickListener {
                    val isChecked = settingModel.getValue()
                    settingModel.saveValue(!isChecked)
                    iv_boolean_setting_checked.isSelected = !isChecked
                    settingModel.callback?.invoke(settingModel)
                }
            }
        }

        fun bindNormalViewHolder(settingModel: NormalSettingModel)
        {
            with(itemView) {

                settingModel.icon?.let {
                    iv_normal_icon.setImageResource(it)
                }

                tv_normal_setting_name.text = settingModel.name

                setOnClickListener { settingModel.callback.invoke() }
            }
        }

        fun bindRadioViewHolder(settingModel: RadioSettingModel<*, *>)
        {
            with(itemView) {

                settingModel.icon?.let {
                    iv_value_icon.setImageResource(it)
                }

                tv_value_setting_name.text = settingModel.name

                val showValue = when (settingModel)
                {
                    is StringRadioSettingModel -> settingModel.radioMap.getValue(settingModel.getValue())
                    else                       -> ""
                }

                tv_value_setting_value.text = showValue

                val radioList = mutableListOf<String>()
                settingModel.radioMap.forEach { radioList.add(it.value) }

                setOnClickListener {
                    PopupUtil.showRadioGroupPopup(settingModel.name, radioList, showValue) { result ->
                        settingModel.radioMap.forEach {
                            if (it.value == result)
                            {
                                setData(data.indexOf(settingModel), settingModel)
                                if (settingModel is StringRadioSettingModel)
                                {
                                    settingModel.saveValue(it.key as String)
                                    settingModel.callback.invoke(settingModel)
                                }
                                return@showRadioGroupPopup
                            }
                        }
                    }
                }
            }
        }
    }

    fun update(item: BaseSettingModel)
    {
        setData(getItemPosition(item), item)
    }
}