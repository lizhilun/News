package com.lizl.news.custom.view

import android.content.Context
import com.lizl.news.R
import com.lizl.news.adapter.OperationListAdapter
import com.lizl.news.model.other.OperationItem
import com.lizl.news.mvvm.activity.NewsSourcesConfigActivity
import com.lizl.news.util.ActivityUtil
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

        rv_menu.adapter = OperationListAdapter(mutableListOf<OperationItem>().apply {

            add(OperationItem(context.getString(R.string.news_source), R.drawable.ic_news_source) {
                ActivityUtil.turnToActivity(NewsSourcesConfigActivity::class.java)
            })
        })
    }
}