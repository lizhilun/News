package com.lizl.news.model.setting

import com.lizl.news.config.util.ConfigUtil
import kotlinx.coroutines.runBlocking

class BooleanSettingModel(val name: String, val key: String, val icon: Int? = null, val callback: ((BooleanSettingModel) -> Unit)? = null) : BaseSettingModel()
{
    fun getValue() = runBlocking { ConfigUtil.getBoolean(key) }

    suspend fun saveValue(value: Boolean) = ConfigUtil.set(key, value)
}