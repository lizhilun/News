package com.lizl.news.model.todayinhistory

import com.google.gson.annotations.SerializedName

class TodayInHistoryResponseModel
{
    @SerializedName("error_code")
    val errorCode = ""

    @SerializedName("reason")
    val reason = ""

    @SerializedName("result")
    val resultList: List<ResultModel>? = null

    class ResultModel
    {
        @SerializedName("day")
        val day = 0

        @SerializedName("des")
        val des = ""

        @SerializedName("id")
        val id = ""

        @SerializedName("lunar")
        val lunar = ""

        @SerializedName("month")
        val month = 0

        @SerializedName("pic")
        val pic = ""

        @SerializedName("title")
        val title = ""

        @SerializedName("year")
        val year = 0
    }
}