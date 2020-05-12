package com.lizl.news.model.news.headline

import com.google.gson.annotations.SerializedName

class HeadlineResponseModel
{
    @SerializedName("code")
    val code = ""

    @SerializedName("msg")
    val msg = ""

    @SerializedName("newslist")
    val newsList: List<NewsModel>? = null

    class NewsModel
    {
        @SerializedName("ctime")
        val ctime = ""

        @SerializedName("title")
        val title = ""

        @SerializedName("description")
        val description = ""

        @SerializedName("picUrl")
        val picUrl = ""

        @SerializedName("url")
        val url = ""
    }
}