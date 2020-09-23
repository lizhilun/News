package com.lizl.news.model.setting

import com.lizl.news.config.util.ConfigUtil

class BooleanSettingModel(val name: String, val key: String, val icon: Int? = null, val callback: ((BooleanSettingModel) -> Unit)? = null) : BaseSettingModel()
{
    fun getValue() = ConfigUtil.getBoolean(key)

    fun saveValue(value: Boolean) = ConfigUtil.set(key, value)
}