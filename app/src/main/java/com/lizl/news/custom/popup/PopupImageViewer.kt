package com.lizl.news.custom.popup

import android.content.Context
import androidx.core.view.isVisible
import com.lizl.news.R
import com.lizl.news.adapter.ImagePagerAdapter
import com.lizl.news.custom.function.registerOnPageChangeCallback
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.impl.FullScreenPopupView
import kotlinx.android.synthetic.main.popup_image_viewer.view.*

class PopupImageViewer(context: Context, private val imageList: MutableList<String>, private val position: Int) : FullScreenPopupView(context)
{
    override fun getImplLayoutId() = R.layout.popup_image_viewer

    override fun onCreate()
    {
        popupInfo?.let {
            it.hasShadowBg = true
            it.popupAnimation = PopupAnimation.ScaleAlphaFromCenter
        }

        val imagePagerAdapter = ImagePagerAdapter(imageList)
        rv_image_pager.adapter = imagePagerAdapter
        tv_indicator.bringToFront()

        rv_image_pager.setCurrentItem(position, false)

        tv_indicator.isVisible = imageList.size > 1
        tv_indicator.text = "${position + 1}/${imageList.size}"

        rv_image_pager.registerOnPageChangeCallback { position ->
            tv_indicator.text = "${position + 1}/${imageList.size}"
        }

        imagePagerAdapter.setOnOutsidePhotoTapListener { dismiss() }
    }
}