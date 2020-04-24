package com.lizl.news.model

data class NewsModel(val detailUrl: String, val title: String, val coverImageUrl: String? = null, val platform: String)