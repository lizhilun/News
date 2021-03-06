package com.lizl.news.util

import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.lizl.news.GlideApp
import com.lizl.news.R
import com.lizl.news.config.AppConfig
import com.lizl.news.constant.AppConstant
import com.lizl.news.mvvm.activity.WebViewActivity
import com.zzhoujay.richtext.CacheType
import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.RichText
import com.zzhoujay.richtext.RichType
import com.zzhoujay.richtext.callback.ImageFixCallback
import com.zzhoujay.richtext.ig.DefaultImageGetter

object DataBindUtil
{
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
        if (AppConfig.isNoImage())
        {
            GlideApp.with(imageView).load(R.drawable.ic_baseline_broken_image_24).into(imageView)
        }
        else
        {
            GlideApp.with(imageView).load(imageUri).into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("app:imageUri")
    fun bindImageUrl(imageView: ImageView, imageResId: Int?)
    {
        imageResId ?: return
        imageView.setImageResource(imageResId)
    }

    @JvmStatic
    @BindingAdapter("app:url")
    fun bindWebViewUrl(webView: WebView, url: String?)
    {
        webView.loadUrl(url)
    }

    @JvmStatic
    @BindingAdapter("app:strText")
    fun bindText(textView: TextView, strText: Any?)
    {
        textView.text = strText?.toString()
    }

    @JvmStatic
    @BindingAdapter("app:isSelected")
    fun bindSelected(view: View, isSelected: Boolean)
    {
        view.isSelected = isSelected
    }

    @JvmStatic
    @BindingAdapter("app:overScrollMode")
    fun bindOverScrollModel(viewPager: ViewPager2, mode: Int)
    {
        val child = viewPager.getChildAt(0)
        if (child is RecyclerView)
        {
            child.overScrollMode = mode
        }
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
            }).noImage(AppConfig.isNoImage()) // 不显示并且不加载图片
            .resetSize(false) // 默认false，是否忽略img标签中的宽高尺寸（只在img标签中存在宽高时才有效），
            // true：忽略标签中的尺寸并触发SIZE_READY回调，false：使用img标签中的宽高尺寸，不触发SIZE_READY回调
            .clickable(true) // 是否可点击，默认只有设置了点击监听才可点击
            .imageClick { imageUrls, position -> PopupUtil.showImageViewerPopup(imageUrls[position]) } // 设置图片点击回调
            .urlClick { url ->
                ActivityUtil.turnToActivity(WebViewActivity::class.java, Pair(AppConstant.BUNDLE_URL, url))
                true
            } // 设置链接点击回调
            .cache(CacheType.all) // 缓存类型，默认为Cache.ALL（缓存图片和图片大小信息和文本样式信息）
            .into(textView) // 设置目标TextView
    }
}