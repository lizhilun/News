package com.lizl.news.model.news.dailyhot.briefing

import com.google.gson.annotations.SerializedName

class DailyBriefingResponseModel
{
    @SerializedName("code")
    val code = ""

    @SerializedName("msg")
    val msg = ""

    @SerializedName("newslist")
    val newsList: List<NewsModel>? = null

    class NewsModel
    {
        @SerializedName("mtime")
        val mtime = ""

        @SerializedName("title")
        val title = ""

        @SerializedName("digest")
        val digest = ""

        @SerializedName("imgsrc")
        val imgsrc = ""

        @SerializedName("url")
        val url = ""

        @SerializedName("source")
        val source = ""
    }
}