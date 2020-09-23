package com.lizl.news.model.setting

open class BaseSettingModel(open val name: String, open val icon: Int = -1, open val callback: () -> Unit)