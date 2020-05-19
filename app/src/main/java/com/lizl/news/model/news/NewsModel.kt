package com.lizl.news.model.news

data class NewsModel(val detailUrl: String, val title: String, val coverImageList: List<String>, val source: String)
{
    constructor(detailUrl: String, title: String, coverImage: String?, source: String) : this(detailUrl, title,
            if (coverImage.isNullOrBlank()) emptyList<String>() else listOf(coverImage), source)
}