package com.lizl.news.util

import android.util.Log
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.zzhoujay.richtext.CacheType
import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.RichText
import com.zzhoujay.richtext.RichType
import com.zzhoujay.richtext.callback.ImageFixCallback
import com.zzhoujay.richtext.ig.DefaultImageGetter
import java.lang.Exception

object DataBindUtil
{
    private val TAG = "DataBindUtil"

    @JvmStatic
    @BindingAdapter("app:adapter")
    fun bindAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?)
    {
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("app:imageUri")
    fun bindImageUrl(imageView: ImageView, imageUri: String?)
    {
        if (imageUri.isNullOrEmpty()) return
        GlideUtil.displayImage(imageView, imageUri)
    }

    @JvmStatic
    @BindingAdapter("app:url")
    fun bindWebViewUrl(webView: WebView, url: String?)
    {
        webView.loadUrl(url)
    }

    @JvmStatic
    @BindingAdapter("app:fragmentPagerAdapter")
    fun bindFragmentPagerAdapter(viewPager: ViewPager, fragmentPagerAdapter: FragmentPagerAdapter?)
    {
        viewPager.adapter = fragmentPagerAdapter
    }

    @JvmStatic
    @BindingAdapter("app:offscreenPageLimit")
    fun bindFragmentPagerAdapter(viewPager: ViewPager, offscreenPageLimit: Int?)
    {
        viewPager.offscreenPageLimit = offscreenPageLimit ?: 1
    }

    @JvmStatic
    @BindingAdapter("app:strText")
    fun bindText(textView: TextView, strText: Any?)
    {
        textView.text = strText?.toString()
    }

    @JvmStatic
    @BindingAdapter("app:htmlText")
    fun bindHtmlText(textView: TextView, htmlString: String?)
    {
        if (htmlString.isNullOrBlank()) return

        RichText.from(htmlString) // 数据源
            .autoFix(true) // 是否自动修复，默认true
            .type(RichType.html).autoPlay(true) // gif图片是否自动播放
            .showBorder(false) // 是否显示图片边框
            .imageGetter(DefaultImageGetter()).scaleType(ImageHolder.ScaleType.center_crop) // 图片缩放方式
            .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT) // 图片占位区域的宽高
            .fix(object : ImageFixCallback
            {
                override fun onInit(holder: ImageHolder?)
                {
                }

                override fun onFailure(holder: ImageHolder?, e: Exception?)
                {
                    Log.d(TAG, "onFailure() called with: e = [$e]")
                    holder?.isShow = false
                    holder?.setSize(0, 0)
                }

                override fun onLoading(holder: ImageHolder?)
                {
                }

                override fun onSizeReady(holder: ImageHolder?, imageWidth: Int, imageHeight: Int, sizeHolder: ImageHolder.SizeHolder?)
                {
                }

                override fun onImageReady(holder: ImageHolder?, width: Int, height: Int)
                {
                }
            }).noImage(false) // 不显示并且不加载图片
            .resetSize(false) // 默认false，是否忽略img标签中的宽高尺寸（只在img标签中存在宽高时才有效），
            // true：忽略标签中的尺寸并触发SIZE_READY回调，false：使用img标签中的宽高尺寸，不触发SIZE_READY回调
            .clickable(true) // 是否可点击，默认只有设置了点击监听才可点击
            .imageClick { imageUrls, position ->
                PopupUtil.showImageViewerPopup(imageUrls, position)
            } // 设置图片点击回调
            .urlClick { url -> false } // 设置链接点击回调
            .cache(CacheType.all) // 缓存类型，默认为Cache.ALL（缓存图片和图片大小信息和文本样式信息）
            .into(textView) // 设置目标TextView
    }
}