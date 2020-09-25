package com.lizl.news.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.blankj.utilcode.util.Utils
import com.lizl.news.constant.AppConstant
import com.lizl.news.model.news.colletion.NewsCollectionModel
import com.lizl.news.model.news.source.NewsSourceModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Database(entities = [NewsCollectionModel::class, NewsSourceModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase()
{
    private object Singleton
    {
        val singleton: AppDatabase = Room.databaseBuilder(Utils.getApp(), AppDatabase::class.java, "News.db").addCallback(object : RoomDatabase.Callback()
        {
            override fun onCreate(db: SupportSQLiteDatabase)
            {
                val sourceList = listOf(AppConstant.NEWS_SOURCE_ZHIHU_TOP, AppConstant.NEWS_SOURCE_ZHIHU_DAILY, AppConstant.NEWS_SOURCE_DAILY_GAME_CORE,
                        AppConstant.NEWS_SOURCE_DAILY_YYS, AppConstant.NEWS_SOURCE_DAILY_36_KR, AppConstant.NEWS_SOURCE_DAILY_IT_HOME,
                        AppConstant.NEWS_SOURCE_DAILY_SS_PAI, AppConstant.NEWS_SOURCE_DAILY_HU_XIU, AppConstant.NEWS_SOURCE_DAILY_JIAN_DAN,
                        AppConstant.NEWS_SOURCE_DAILY_HU_PU, AppConstant.NEWS_SOURCE_DAILY_THE_PAPER, AppConstant.NEWS_SOURCE_DAILY_V2EX,
                        AppConstant.NEWS_SOURCE_DAILY_WEIBO)

                mutableListOf<NewsSourceModel>().apply {
                    var index = 0
                    sourceList.forEach { add(NewsSourceModel(newSource = it, position = index++, visible = true)) }
                    GlobalScope.launch {
                        //TODO:延时处理防止死锁
                        delay(200)
                        instance.getNewsSourceDao().insertList(this@apply)
                    }
                }
            }
        }).allowMainThreadQueries().build()
    }

    companion object
    {
        val instance = Singleton.singleton
    }

    abstract fun getNewsCollectionDao(): NewsCollectionDao

    abstract fun getNewsSourceDao(): NewsSourceDao
}