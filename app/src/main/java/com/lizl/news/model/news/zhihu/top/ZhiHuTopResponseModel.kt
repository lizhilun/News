package com.lizl.news.model.news.zhihu.top

import com.google.gson.annotations.SerializedName

class ZhiHuTopResponseModel
{
    @SerializedName("fresh_text")
    val freshText = ""

    @SerializedName("data")
    val dataList: List<DataModel>? = null

    class DataModel
    {
        @SerializedName("style_type")
        val styleType = ""

        @SerializedName("detail_text")
        val detailText = ""

        @SerializedName("trend")
        val trend = ""

        @SerializedName("debut")
        val debut = ""

        @SerializedName("card_id")
        val cardId = ""

        @SerializedName("attached_info")
        val attachedInfo = ""

        @SerializedName("type")
        val type = ""

        @SerializedName("id")
        val id = ""

        @SerializedName("target")
        val target: TargetModel? = null

        @SerializedName("children")
        val children: List<ChildrenModel>? = null
    }

    class TargetModel
    {
        @SerializedName("bound_topic_ids")
        val bound_topic_ids: List<Int>? = null

        @SerializedName("excerpt")
        val excerpt = ""

        @SerializedName("answer_count")
        val answer_count = ""

        @SerializedName("is_following")
        val is_following = false

        @SerializedName("id")
        val id = ""

        @SerializedName("author")
        val author: AuthorModel? = null

        @SerializedName("url")
        val url = ""

        @SerializedName("title")
        val title = ""

        @SerializedName("created")
        val created = ""

        @SerializedName("comment_count")
        val comment_count = ""

        @SerializedName("follower_count")
        val follower_count = ""

        @SerializedName("type")
        val type = ""
    }

    class AuthorModel
    {
        @SerializedName("headline")
        val headline = ""

        @SerializedName("avatar_url")
        val avatar_url = ""

        @SerializedName("name")
        val name = ""

        @SerializedName("url")
        val url = ""

        @SerializedName("url_token")
        val url_token = ""

        @SerializedName("type")
        val type = ""

        @SerializedName("user_type")
        val user_type = ""

        @SerializedName("id")
        val id = ""
    }

    class ChildrenModel
    {
        @SerializedName("type")
        val type = ""

        @SerializedName("thumbnail")
        val thumbnail = ""
    }
}