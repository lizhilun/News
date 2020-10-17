package com.lizl.news.model.news.shieldword

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shieldWords")
class ShieldWordModel(@PrimaryKey(autoGenerate = true)
                      var id: Long = 0L,

                      @ColumnInfo
                      var word: String = "")