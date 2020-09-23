package com.lizl.news.util

import android.app.Application
import android.content.res.Configuration
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.Utils
import com.lizl.news.R
import com.lizl.news.config.AppConfig
import com.lizl.news.config.constant.ConfigConstant
import skin.support.SkinCompatManager
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.design.app.SkinMaterialViewInflater

object SkinUtil
{
    private const val SKIN_DARK = "dark"

    fun init(application: Application)
    {
        SkinCompatManager.withoutActivity(application)                         // 基础控件换肤初始化
            .addInflater(SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
            .addInflater(SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
            .setSkinWindowBackgroundEnable(false)               // windowBg换肤
        loadSkin()
    }

    fun loadSkin()
    {
        if (isNightModeOn())
        {
            SkinCompatManager.getInstance().loadSkin(SKIN_DARK, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN)
        }
        else
        {
            SkinCompatManager.getInstance().restoreDefaultTheme()
        }
    }

    fun getGlobalTextColor(): Int
    {
        return if (isNightModeOn())
        {
            ContextCompat.getColor(Utils.getApp(), R.color.colorTextColor_night)
        }
        else
        {
            ContextCompat.getColor(Utils.getApp(), R.color.colorTextColor)
        }
    }

    fun isNightModeOn(): Boolean
    {
        return when (AppConfig.getDarkModel())
        {
            ConfigConstant.APP_NIGHT_MODE_ON            -> true
            ConfigConstant.APP_NIGHT_MODE_OFF           -> false
            ConfigConstant.APP_NIGHT_MODE_FOLLOW_SYSTEM -> isSystemDarkMode()
            else                                        -> false
        }
    }

    private fun isSystemDarkMode(): Boolean
    {
        return Utils.getApp().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

}