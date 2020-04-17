package com.lizl.news.util

import android.widget.ImageView
import com.lizl.news.GlideApp

object GlideUtil
{
    /**
     * 加载图片并显示
     */
    fun displayImage(imageView: ImageView, imageUri: String)
    {
        GlideApp.with(imageView).load(imageUri).into(imageView)
    }
}