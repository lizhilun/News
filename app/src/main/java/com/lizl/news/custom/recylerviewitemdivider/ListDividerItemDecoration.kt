package com.lizl.news.custom.recylerviewitemdivider

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ListDividerItemDecoration(private val dividerSize: Int) : RecyclerView.ItemDecoration()
{
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
    {
        outRect.top = if (parent.getChildLayoutPosition(view) == 0) 0 else dividerSize
    }
}