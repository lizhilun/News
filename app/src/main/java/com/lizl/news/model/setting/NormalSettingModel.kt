package com.lizl.news.model.setting

class NormalSettingModel(val name: String, val icon: Int? = null, val callback: () -> Unit) : BaseSettingModel()