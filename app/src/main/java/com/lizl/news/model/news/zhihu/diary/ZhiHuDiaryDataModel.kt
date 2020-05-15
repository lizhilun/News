package com.lizl.news.model.news.zhihu.diary

import com.google.gson.annotations.SerializedName

class ZhiHuDiaryDataModel
{
    @SerializedName("date")
    val date = ""

    @SerializedName("stories")
    val storyList: List<DiaryStoryModel>? = null

    class DiaryStoryModel
    {
        @SerializedName("image_hue")
        val imageHue = ""

        @SerializedName("title")
        val title = ""

        @SerializedName("url")
        val url = ""

        @SerializedName("hint")
        val hint = ""

        @SerializedName("ga_prefix")
        val gaPrefix = ""

        @SerializedName("images")
        val imageList: List<String>? = null

        @SerializedName("type")
        val type = 0

        @SerializedName("id")
        val id = ""
    }
}