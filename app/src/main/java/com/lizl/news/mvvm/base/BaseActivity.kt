package com.lizl.news.mvvm.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.SkinAppCompatDelegateImpl
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.BarUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lizl.news.R
import com.lizl.news.constant.EventConstant
import com.lizl.news.util.SkinUtil

open class BaseActivity<DB : ViewDataBinding>(private val layoutId: Int) : AppCompatActivity()
{
    protected val TAG = this.javaClass.simpleName

    protected lateinit var dataBinding: DB

    override fun onCreate(savedInstanceState: Bundle?)
    {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, layoutId)
        dataBinding.lifecycleOwner = this

        updateStatusBarColor()

        initView()
        initData()

        LiveEventBus.get(EventConstant.EVENT_DARK_MODE_UPDATE).observe(this, Observer { updateStatusBarColor() })
    }

    private fun updateStatusBarColor()
    {
        BarUtils.setStatusBarColor(this, SkinUtil.getColor(this, R.color.colorContentBg))
    }

    override fun onResume()
    {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    override fun onStart()
    {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onRestart()
    {
        Log.d(TAG, "onRestart")
        super.onRestart()
    }

    override fun onPause()
    {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop()
    {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroy()
    {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun getDelegate(): AppCompatDelegate
    {
        return SkinAppCompatDelegateImpl.get(this, this)
    }

    open fun initView()
    {

    }

    open fun initData()
    {

    }
}