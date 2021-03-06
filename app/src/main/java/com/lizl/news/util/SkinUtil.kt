package com.lizl.news.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.Utils
import com.lizl.news.R
import com.lizl.news.config.AppConfig
import com.lizl.news.config.constant.ConfigConstant
import com.lizl.news.config.util.ConfigUtil
import com.lizl.news.custom.skin.CustomSkinActivityLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import skin.support.SkinCompatManager
import skin.support.SkinCompatManager.SkinLoaderListener
import skin.support.app.SkinAppCompatViewInflater
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.content.res.SkinCompatResources
import skin.support.design.app.SkinMaterialViewInflater

object SkinUtil
{
    private const val SKIN_DARK = "dark"

    private val nightModeLiveData = MutableLiveData<Boolean>()

    fun init(application: Application)
    {
        SkinCompatManager.withoutActivity(application).addInflater(SkinAppCompatViewInflater())           // 基础控件换肤初始化
            .addInflater(SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
            .addInflater(SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
            .setSkinWindowBackgroundEnable(true)               // windowBg换肤

        runBlocking { loadSkin() }

        application.registerActivityLifecycleCallbacks(CustomSkinActivityLifecycle)

        ConfigUtil.obConfig(ConfigConstant.CONFIG_DARK_MODE).observeForever { GlobalScope.launch { loadSkin() } }
    }

    private suspend fun loadSkin()
    {
        val skinLoadListener = object : SkinLoaderListener
        {
            override fun onSuccess()
            {
                GlobalScope.launch {
                    nightModeLiveData.postValue(isNightModeOn())
                    updateStatusBarColor()
                }
            }

            override fun onFailed(errMsg: String?)
            {
            }

            override fun onStart()
            {
            }
        }

        if (isNightModeOn())
        {
            SkinCompatManager.getInstance().loadSkin(SKIN_DARK, skinLoadListener, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN)
        }
        else
        {
            SkinCompatManager.getInstance().loadSkin("", skinLoadListener, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN)
        }
    }

    fun obNightMode() = nightModeLiveData

    fun getColor(context: Context, colorResId: Int) = SkinCompatResources.getColor(context, colorResId)

    fun updateStatusBarColor(activity: Activity? = ActivityUtils.getTopActivity())
    {
        activity ?: return
        GlobalScope.launch(Dispatchers.Main) {
            BarUtils.setStatusBarColor(activity, getColor(activity, R.color.colorContentBg))
        }
    }

    private suspend fun isNightModeOn(): Boolean
    {
        return when (AppConfig.getDarkModel())
        {
            ConfigConstant.APP_NIGHT_MODE_ON -> true
            ConfigConstant.APP_NIGHT_MODE_OFF -> false
            ConfigConstant.APP_NIGHT_MODE_FOLLOW_SYSTEM -> isSystemDarkMode()
            else                                        -> false
        }
    }

    private fun isSystemDarkMode(): Boolean
    {
        return Utils.getApp().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}