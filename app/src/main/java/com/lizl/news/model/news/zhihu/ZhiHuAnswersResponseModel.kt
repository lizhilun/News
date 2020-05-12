package com.lizl.news.model.news.zhihu

import com.google.gson.annotations.SerializedName

class ZhiHuAnswersResponseModel
{
    @SerializedName("data")
    val dataList: List<DataModel>? = null

    @SerializedName("paging")
    val paging: PagingModel? = null

    class DataModel
    {
        @SerializedName("id")
        val id = ""

        @SerializedName("type")
        val type = ""

        @SerializedName("answer_type")
        val answer_type = ""

        @SerializedName("question")
        val question: QuestionModel? = null

        @SerializedName("author")
        val author: AuthorModel? = null

        @SerializedName("reshipment_settings")
        val reshipment_settings = ""

        @SerializedName("content")
        val content = ""

        @SerializedName("editable_content")
        val editable_content = ""

        @SerializedName("excerpt")
        val excerpt = ""

        @SerializedName("collapsed_by")
        val collapsed_by = ""

        @SerializedName("collapse_reason")
        val collapse_reason = ""

        @SerializedName("annotation_action")
        val annotation_action = ""
    }

    class QuestionModel
    {
        @SerializedName("type")
        val type = ""

        @SerializedName("id")
        val id = ""

        @SerializedName("title")
        val title = ""

        @SerializedName("question_type")
        val question_type = ""

        @SerializedName("created")
        val created = 0L

        @SerializedName("updated_time")
        val updated_time = ""

        @SerializedName("url")
        val url = ""
    }

    class AuthorModel
    {
        @SerializedName("id")
        val id = ""

        @SerializedName("url_token")
        val url_token = ""

        @SerializedName("name")
        val name = ""

        @SerializedName("avatar_url")
        val avatar_url = ""

        @SerializedName("avatar_url_template")
        val avatar_url_template = ""

        @SerializedName("is_org")
        val is_org = ""

        @SerializedName("type")
        val type = ""

        @SerializedName("url")
        val url = ""

        @SerializedName("user_type")
        val user_type = ""

        @SerializedName("headline")
        val headline = ""

        @SerializedName("gender")
        val gender = ""

        @SerializedName("is_advertiser")
        val is_advertiser = false

        @SerializedName("follower_count")
        val follower_count = 0L

        @SerializedName("is_followed")
        val is_followed = false

        @SerializedName("is_privacy")
        val is_privacy = false
    }

    class PagingModel
    {
        @SerializedName("is_end")
        val is_end = false

        @SerializedName("is_start")
        val is_start = false

        @SerializedName("next")
        val next = ""

        @SerializedName("previous")
        val previous = ""

        @SerializedName("totals")
        val totals = 0L
    }
}