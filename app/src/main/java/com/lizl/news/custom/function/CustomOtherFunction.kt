package com.lizl.news.custom.function

fun String.deleteStr(str: String): String
{
    return replace(str, "")
}