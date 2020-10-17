package com.lizl.news.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.lizl.news.model.news.shieldword.ShieldWordModel

@Dao
interface ShieldWordDao : BaseDao<ShieldWordModel>
{
    @Query("select * from shieldWords")
    fun getAllShieldWords(): MutableList<ShieldWordModel>

    @Query("select * from shieldWords")
    fun getAllShieldWordsLiveData(): LiveData<MutableList<ShieldWordModel>>

    @Query("select * from shieldWords where :content like '%' || word || '%'")
    fun findShieldWordInContent(content: String): ShieldWordModel?

    @Query("select * from shieldWords where word == :word")
    fun findShieldWord(word: String): ShieldWordModel?
}