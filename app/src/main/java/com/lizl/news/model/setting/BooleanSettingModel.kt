package com.lizl.news.model.setting

class BooleanSettingModel(override val name: String, override val icon: Int = -1, var isChecked: Boolean, override val callback: () -> Unit) :
    BaseSettingModel(name, icon, callback)