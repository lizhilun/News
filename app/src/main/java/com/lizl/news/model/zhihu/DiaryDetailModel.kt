package com.lizl.news.model.zhihu

import com.google.gson.annotations.SerializedName

class DiaryDetailModel
{
    @SerializedName("body")
    val body = ""

    @SerializedName("image_hue")
    val imageHue = ""

    @SerializedName("image_source")
    val imageSource = ""

    @SerializedName("title")
    val title = ""

    @SerializedName("url")
    val url = ""

    @SerializedName("image")
    val image = ""

    @SerializedName("share_url")
    val shareUrl = ""

    @SerializedName("ga_prefix")
    val gaPrefix = ""

    @SerializedName("images")
    val images: List<String>? = null

    @SerializedName("type")
    val type = 0

    @SerializedName("id")
    val id = ""
}