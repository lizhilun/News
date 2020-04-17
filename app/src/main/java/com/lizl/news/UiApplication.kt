package com.lizl.news

import android.app.Application
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.Utils
import com.scwang.smartrefresh.header.DeliveryHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.zzhoujay.richtext.RichText

class UiApplication : Application()
{
    init
    {
        instance = this
    }

    companion object
    {
        lateinit var instance: UiApplication
    }

    override fun onCreate()
    {
        super.onCreate()

        Utils.init(this)

        RichText.initCacheDir(this)

        //初始化SmartRefreshLayout全局Header
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, R.color.white) //全局设置主题颜色
            DeliveryHeader(context).apply { setBackgroundColor(ContextCompat.getColor(context, R.color.colorDivideView)) }
        }
        //初始化SmartRefreshLayout全局Footer
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> BallPulseFooter(context) }
    }
}