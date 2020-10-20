package com.lizl.news.model.setting

class NormalSettingModel(val name: String, val icon: Int? = null, var value: String = "", val callback: (NormalSettingModel) -> Unit) : BaseSettingModel()