package com.lizl.news.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.lizl.news.model.news.source.NewsSourceModel

@Dao
interface NewsSourceDao : BaseDao<NewsSourceModel>
{
    @Query("select count (*) from newsSources")
    fun getAllNewsSourceCount(): Int

    @Query("select * from newsSources order by position asc")
    fun getAllNewsSource(): MutableList<NewsSourceModel>

    @Query("select * from newsSources order by position asc")
    fun getAllNewSourceLiveData(): LiveData<MutableList<NewsSourceModel>>

    @Query("select * from newsSources where visible order by position asc")
    fun getVisibleNewsSourceLiveData(): LiveData<MutableList<NewsSourceModel>>
}