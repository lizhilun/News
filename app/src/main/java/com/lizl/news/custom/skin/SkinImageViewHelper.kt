package com.lizl.news.custom.skin

import android.content.res.TypedArray
import android.util.AttributeSet
import com.lizl.news.R.styleable
import skin.support.content.res.SkinCompatResources
import skin.support.content.res.SkinCompatVectorResources
import skin.support.widget.SkinCompatHelper

class SkinImageViewHelper(private val view: SkinImageView)
{
    private var mSrcResId = SkinCompatHelper.INVALID_ID
    private var mTintResId = SkinCompatHelper.INVALID_ID
    private var mSrcCompatResId = SkinCompatHelper.INVALID_ID

    fun loadFromAttributes(attrs: AttributeSet?, defStyleAttr: Int)
    {
        var a: TypedArray? = null
        try
        {
            a = view.context.obtainStyledAttributes(attrs, styleable.SkinImageView, defStyleAttr, 0)
            mSrcResId = a.getResourceId(styleable.SkinImageView_android_src, SkinCompatHelper.INVALID_ID)
            mTintResId = a.getResourceId(styleable.SkinImageView_android_tint, SkinCompatHelper.INVALID_ID)
            mSrcCompatResId = a.getResourceId(styleable.SkinImageView_srcCompat, SkinCompatHelper.INVALID_ID)
        }
        finally
        {
            a?.recycle()
        }
        applySkin()
    }

    fun setImageResource(resId: Int)
    {
        mSrcResId = resId
        applySkin()
    }

    fun applySkin()
    {
        mSrcCompatResId = SkinCompatHelper.checkResourceId(mSrcCompatResId)
        if (mSrcCompatResId != SkinCompatHelper.INVALID_ID)
        {
            val drawable = SkinCompatVectorResources.getDrawableCompat(view.context, mSrcCompatResId)
            if (drawable != null)
            {
                view.setImageDrawable(drawable)
            }
        }
        else
        {
            mSrcResId = SkinCompatHelper.checkResourceId(mSrcResId)
            if (mSrcResId == SkinCompatHelper.INVALID_ID)
            {
                return
            }
            val drawable = SkinCompatVectorResources.getDrawableCompat(view.context, mSrcResId)
            if (drawable != null)
            {
                view.setImageDrawable(drawable)
            }
        }

        applyTintResource()
    }

    private fun applyTintResource()
    {
        mTintResId = SkinCompatHelper.checkResourceId(mTintResId)
        if (mTintResId != SkinCompatHelper.INVALID_ID)
        {
            try
            {
                val color = SkinCompatResources.getColorStateList(view.context, mTintResId)
                view.imageTintList = color
            }
            catch (e: Exception)
            {
            }
        }
    }
}