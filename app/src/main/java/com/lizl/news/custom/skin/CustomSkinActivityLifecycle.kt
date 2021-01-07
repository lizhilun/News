package com.lizl.news.custom.skin

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.lizl.news.util.SkinUtil

object CustomSkinActivityLifecycle : Application.ActivityLifecycleCallbacks
{
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?)
    {
        SkinUtil.updateStatusBarColor(activity)
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