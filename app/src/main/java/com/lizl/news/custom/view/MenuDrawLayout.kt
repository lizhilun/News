package com.lizl.news.custom.view

import android.content.Context
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lizl.news.R
import com.lizl.news.adapter.SettingListAdapter
import com.lizl.news.config.constant.ConfigConstant
import com.lizl.news.constant.EventConstant
import com.lizl.news.dao.AppDatabase
import com.lizl.news.model.setting.*
import com.lizl.news.mvvm.activity.NewsCollectionActivity
import com.lizl.news.mvvm.activity.NewsSourcesConfigActivity
import com.lizl.news.util.ActivityUtil
import com.lizl.news.util.SkinUtil
import com.lxj.xpopup.core.DrawerPopupView
import kotlinx.android.synthetic.main.layout_drawer_menu.view.*

class MenuDrawLayout(context: Context) : DrawerPopupView(context)
{
    override fun getImplLayoutId() = R.layout.layout_drawer_menu

    override fun onCreate()
    {
        super.onCreate()

        tv_app_introduce.text = context.getString(R.string.app_introduce, AppDatabase.instance.getNewsSourceDao().getAllNewsSourceCount())

        rv_menu.adapter = SettingListAdapter(mutableListOf<BaseSettingModel>().apply {

            add(NormalSettingModel(context.getString(R.string.news_source), R.drawable.ic_baseline_news_source_24) {
                ActivityUtil.turnToActivity(NewsSourcesConfigActivity::class.java)
            })

            val darkModeMap = mapOf(ConfigConstant.APP_NIGHT_MODE_ON to context.getString(R.string.on),
                    ConfigConstant.APP_NIGHT_MODE_OFF to context.getString(R.string.off),
                    ConfigConstant.APP_NIGHT_MODE_FOLLOW_SYSTEM to context.getString(R.string.follow_system))

            add(StringRadioSettingModel(context.getString(R.string.dark_mode_config), ConfigConstant.CONFIG_DARK_MODE, R.drawable.ic_baseline_dark_mode_24,
                    darkModeMap) {
                SkinUtil.loadSkin {
                    LiveEventBus.get(EventConstant.EVENT_DARK_MODE_UPDATE).post(true)
                }
            })

            add(BooleanSettingModel(context.getString(R.string.no_image_config), ConfigConstant.CONFIG_NO_IMAGE, R.drawable.ic_baseline_broken_image_24))

            add(DividerSettingModel())

            add(NormalSettingModel(context.getString(R.string.news_collection), R.drawable.ic_baseline_collections_24) {
                ActivityUtil.turnToActivity(NewsCollectionActivity::class.java)
            })
        })
    }
}