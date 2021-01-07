package com.lizl.news.custom.skin

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import com.lizl.news.R
import com.lizl.news.util.SkinUtil

object CustomSkinActivityLifecycle : Application.ActivityLifecycleCallbacks
{
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?)
    {
        BarUtils.setStatusBarColor(activity, SkinUtil.getColor(activity, R.color.colorContentBg))
    }

    override fun onActivityStarted(activity: Activity)
    {
    }

    override fun onActivityResumed(activity: Activity)
    {
    }

    override fun onActivityPaused(activity: Activity)
    {
    }

    override fun onActivityStopped(activity: Activity)
    {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle)
    {
    }

    override fun onActivityDestroyed(activity: Activity)
    {
    }
}