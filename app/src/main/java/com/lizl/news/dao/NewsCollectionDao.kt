package com.lizl.news.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.lizl.news.model.news.colletion.NewsCollectionModel

@Dao
interface NewsCollectionDao : BaseDao<NewsCollectionModel>
{
    @Query("select * from newsCollections")
    fun getAllNewConnectionLiveData(): LiveData<MutableList<NewsCollectionModel>>

    @Query("select * from newsCollections where newsTitle == :title and newsUrl == :url")
    fun findCollection(title: String, url: String): LiveData<NewsCollectionModel?>
}