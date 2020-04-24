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
}