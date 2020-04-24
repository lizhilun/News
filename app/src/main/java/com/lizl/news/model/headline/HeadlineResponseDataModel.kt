package com.lizl.news.model.headline

import com.google.gson.annotations.SerializedName

class HeadlineResponseDataModel
{
    @SerializedName("stat")
    val stat = ""

    @SerializedName("data")
    val headlineList: List<HeadlineModel>? = null
}