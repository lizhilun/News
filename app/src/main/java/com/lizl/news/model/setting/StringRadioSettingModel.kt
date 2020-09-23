package com.lizl.news.model.setting

import com.lizl.news.config.util.ConfigUtil

class StringRadioSettingModel(override val name: String, override val key: String, override val icon: Int? = null, override val radioMap: Map<String, String>,
        override val callback: (StringRadioSettingModel) -> Unit) : RadioSettingModel<String, StringRadioSettingModel>(name, key, icon, radioMap, callback)
{
    override fun getValue() = ConfigUtil.getString(key)

    override fun saveValue(value: String) = ConfigUtil.set(key, value)
}