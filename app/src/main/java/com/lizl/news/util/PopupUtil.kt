package com.lizl.news.util

import com.blankj.utilcode.util.ActivityUtils
import com.lizl.news.custom.popup.PopupImageViewer
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import kotlinx.coroutines.*


object PopupUtil
{
    private var curPopup: BasePopupView? = null
    private var showPopupJob: Job? = null

    fun showImageViewerPopup(imageUrl: String)
    {
        showImageViewerPopup(mutableListOf(imageUrl), 0)
    }

    fun showImageViewerPopup(imageList: MutableList<String>, position: Int)
    {
        val context = ActivityUtils.getTopActivity() ?: return
        showPopup(XPopup.Builder(context).asCustom(PopupImageViewer(context, imageList, position)))
    }

    fun dismissAll()
    {
        GlobalScope.launch(Dispatchers.Main) {
            showPopupJob?.cancel()
            curPopup?.dismiss()
        }
    }

    private fun showPopup(popup: BasePopupView)
    {
        GlobalScope.launch(Dispatchers.Main) {
            showPopupJob?.cancel()
            showPopupJob = GlobalScope.launch(Dispatchers.Main) {
                if (curPopup?.isShow == true)
                {
                    curPopup?.dismiss()
                    delay(300)
                }
                curPopup = popup
                curPopup?.show()
            }
        }
    }
}