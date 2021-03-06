package com.lizl.news.config.constant

import com.lizl.news.config.annotation.BooleanConfig
import com.lizl.news.config.annotation.StringConfig

object ConfigConstant
{

    const val APP_NIGHT_MODE_ON = "APP_NIGHT_MODE_ON"
    const val APP_NIGHT_MODE_OFF = "APP_NIGHT_MODE_OFF"
    const val APP_NIGHT_MODE_FOLLOW_SYSTEM = "APP_NIGHT_MODE_FOLLOW_SYSTEM"

    //暗黑模式
    @StringConfig(APP_NIGHT_MODE_FOLLOW_SYSTEM)
    const val CONFIG_DARK_MODE = "CONFIG_DARK_MODE"

    //无图模式
    @BooleanConfig(false)
    const val CONFIG_NO_IMAGE = "CONFIG_NO_IMAGE"
}