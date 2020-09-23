package com.lizl.news.model.setting

class TextSettingModel(override val name: String, override val icon: Int = -1, override val callback: () -> Unit) : BaseSettingModel(name, icon, callback)