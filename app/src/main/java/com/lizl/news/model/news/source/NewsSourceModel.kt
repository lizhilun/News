package com.lizl.news.model.news.source

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "newsSources")
class NewsSourceModel(@PrimaryKey(autoGenerate = true)
                      var id: Long = 0L,

                      @ColumnInfo
                      var newSource: String = "",

                      @ColumnInfo
                      var position: Int = 0,

                      @ColumnInfo
                      var visible: Boolean = true)