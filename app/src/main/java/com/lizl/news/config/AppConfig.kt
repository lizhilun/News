package com.lizl.news.config

import com.lizl.news.config.constant.ConfigConstant
import com.lizl.news.config.util.ConfigUtil

object AppConfig
{
    fun getDarkModel() = ConfigUtil.getString(ConfigConstant.CONFIG_DARK_MODE)
}