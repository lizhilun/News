package com.lizl.news.config

import com.lizl.news.config.constant.ConfigConstant
import com.lizl.news.config.util.ConfigUtil
import kotlinx.coroutines.runBlocking

object AppConfig
{
    suspend fun getDarkModel() = ConfigUtil.getString(ConfigConstant.CONFIG_DARK_MODE)

    fun isNoImage() = runBlocking { ConfigUtil.getBoolean(ConfigConstant.CONFIG_NO_IMAGE) }
}