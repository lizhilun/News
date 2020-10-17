package com.lizl.news.util

import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.lizl.news.custom.popup.*
import com.lizl.news.model.other.OperationModel
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

    fun showBindViewOperationListPopup(view: View, operationList: List<OperationModel>)
    {
        val context = ActivityUtils.getTopActivity() ?: return

        val operationIconArray = IntArray(operationList.size) { 0 }
        val operationNameArray = Array(operationList.size) { operationList[it].operationName }

        showPopup(XPopup.Builder(context).atView(view).asAttachList(operationNameArray, operationIconArray) { _, text ->
            operationList.find { it.operationName == text }?.operationItemCallBack?.invoke()
        })
    }

    fun showInfoPopup(content: String)
    {
        val context = ActivityUtils.getTopActivity() ?: return
        showPopup(XPopup.Builder(context).asCustom(PopupInfo(context, content)))
    }

    fun showRadioGroupPopup(title: String, radioList: List<String>, checkedRadio: String, onSelectFinishListener: (result: String) -> Unit)
    {
        val context = ActivityUtils.getTopActivity() ?: return
        showPopup(XPopup.Builder(context).asCustom(PopupRadioGroup(context, title, radioList, checkedRadio, onSelectFinishListener)))
    }

    fun showInputPopup(title: String, onInputFinish: (String) -> Unit)
    {
        val context = ActivityUtils.getTopActivity() ?: return
        showPopup(XPopup.Builder(context).asCustom(PopupInput(context, title, onInputFinish)))
    }

    fun showConfirmPopup(notify: String, onConfirm: () -> Unit)
    {
        val context = ActivityUtils.getTopActivity() ?: return
        showPopup(XPopup.Builder(context).asCustom(PopupConfirm(context, notify, onConfirm)))
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