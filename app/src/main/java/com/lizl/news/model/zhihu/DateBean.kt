package com.lizl.news.model.zhihu

import java.util.*

class DateBean(private val time: Long)
{
    private var year = 0
    private var month = 0
    private var day = 0

    init
    {
        val calendar = Calendar.getInstance()
        calendar.time = Date(time)
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
        day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun lastDay(): DateBean
    {
        return DateBean(time - 24 * 60 * 60 * 1000)
    }

    fun getDateInfo() = String.format(Locale.getDefault(), "%d%02d%02d", year, month, day)
}