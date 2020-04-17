package com.lizl.news.model.zhihu

import com.google.gson.annotations.SerializedName

class DiaryDataModel
{
    @SerializedName("date")
    val date = ""

    @SerializedName("stories")
    val storyList: List<DiaryStoryModel>? = null
}