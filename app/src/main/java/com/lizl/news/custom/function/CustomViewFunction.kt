package com.lizl.news.custom.function

import android.widget.TextView
import androidx.appcompat.widget.Toolbar

fun Toolbar.getTitleTextView(): TextView?
{
    return try
    {
        val titleTextViewField = Toolbar::class.java.getDeclaredField("mTitleTextView")
        titleTextViewField.isAccessible = true
        titleTextViewField.get(this) as TextView
    }
    catch (e: Exception)
    {
        null
    }
}