package com.lizl.news.custom.popup

import android.content.Context
import com.lizl.news.R
import com.lxj.xpopup.core.CenterPopupView
import kotlinx.android.synthetic.main.popup_confirm.view.*

class PopupConfirm(context: Context, private val notify: String, private val onConfirm: () -> Unit) : CenterPopupView(context)
{
    override fun getImplLayoutId() = R.layout.popup_confirm

    override fun onCreate()
    {
        tv_notify.text = notify

        tv_cancel.setOnClickListener { dismiss() }

        tv_confirm.setOnClickListener {
            onConfirm.invoke()
            dismiss()
        }
    }
}