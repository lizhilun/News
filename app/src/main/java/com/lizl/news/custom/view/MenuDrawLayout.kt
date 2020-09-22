package com.lizl.news.custom.view

import android.content.Context
import com.lizl.news.R
import com.lizl.news.util.NewsUtil
import com.lxj.xpopup.core.DrawerPopupView
import kotlinx.android.synthetic.main.layout_drawer_menu.view.*

class MenuDrawLayout(context: Context) : DrawerPopupView(context)
{
    override fun getImplLayoutId() = R.layout.layout_drawer_menu

    override fun onCreate()
    {
        super.onCreate()

        tv_app_introduce.text = context.getString(R.string.app_introduce, NewsUtil.getAllNewsSource().size)
    }
}