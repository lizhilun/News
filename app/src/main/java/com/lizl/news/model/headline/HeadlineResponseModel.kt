package com.lizl.news.model.headline

import com.google.gson.annotations.SerializedName

class HeadlineResponseModel
{
    @SerializedName("reason")
    val reason = ""

    @SerializedName("result")
    val result: HeadlineResponseDataModel? = null

    @SerializedName("error_code")
    val errorCode = ""

    class HeadlineResponseDataModel
    {
        @SerializedName("stat")
        val stat = ""

        @SerializedName("data")
        val headlineList: List<HeadlineModel>? = null
    }

    class HeadlineModel
    {
        @SerializedName("uniquekey")
        val uniquekey = ""

        @SerializedName("title")
        val title = ""

        @SerializedName("date")
        val date = ""

        @SerializedName("category")
        val category = ""

        @SerializedName("author_name")
        val author_name = ""

        @SerializedName("url")
        val url = ""

        @SerializedName("thumbnail_pic_s")
        val thumbnail_pic_s = ""

        @SerializedName("thumbnail_pic_s02")
        val thumbnail_pic_s02 = ""

        @SerializedName("thumbnail_pic_s03")
        val thumbnail_pic_s03 = ""
    }
}