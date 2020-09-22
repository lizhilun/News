package com.lizl.news.custom.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.lizl.news.R

class CustomTitleBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr)
{
    private lateinit var backBtn: AppCompatImageView
    private lateinit var titleTextView: AppCompatTextView
    private lateinit var actionTextView: AppCompatTextView

    private var onBackBtnClickListener: (() -> Unit)? = null
    private var onActionClickListener: (() -> Unit)? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init
    {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?)
    {
        val padding = context.resources.getDimensionPixelOffset(R.dimen.toolbar_back_icon_padding)

        backBtn = AppCompatImageView(context).apply {
            id = generateViewId()
            scaleType = ImageView.ScaleType.FIT_START
            setImageResource(R.drawable.ic_back)
            setPadding(0, padding, 0, padding)
            addView(this)
        }

        titleTextView = AppCompatTextView(context).apply {
            id = generateViewId()
            setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.toolbar_title_text_size))
            setTextColor(ContextCompat.getColor(context, R.color.colorTextColor))
            gravity = Gravity.CENTER_VERTICAL
            addView(this)
        }

        actionTextView = AppCompatTextView(context).apply {
            id = generateViewId()
            gravity = Gravity.CENTER
            setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.toolbar_action_text_size))
            setTextColor(ContextCompat.getColor(context, R.color.colorTextColor))
            setPadding(padding, 0, padding, 0)
            addView(this)
        }

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar)
        backBtn.isVisible = typeArray.getBoolean(R.styleable.CustomTitleBar_backBtnVisible, true)
        titleTextView.text = typeArray.getString(R.styleable.CustomTitleBar_titleText)
        actionTextView.text = typeArray.getString(R.styleable.CustomTitleBar_actionText)
        typeArray.recycle()

        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        constraintSet.constrainHeight(backBtn.id, LayoutParams.MATCH_PARENT)
        constraintSet.constrainWidth(backBtn.id, context.resources.getDimensionPixelOffset(R.dimen.toolbar_back_icon_size))
        constraintSet.connect(backBtn.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)

        constraintSet.constrainHeight(titleTextView.id, LayoutParams.MATCH_PARENT)
        constraintSet.constrainWidth(titleTextView.id, LayoutParams.WRAP_CONTENT)
        constraintSet.connect(titleTextView.id, ConstraintSet.START, backBtn.id, ConstraintSet.END)

        constraintSet.constrainHeight(actionTextView.id, LayoutParams.MATCH_PARENT)
        constraintSet.constrainWidth(actionTextView.id, LayoutParams.WRAP_CONTENT)
        constraintSet.connect(actionTextView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

        constraintSet.applyTo(this)

        backBtn.setOnClickListener { onBackBtnClickListener?.invoke() }

        actionTextView.setOnClickListener { onActionClickListener?.invoke() }
    }

    fun setOnBackBtnClickListener(onBackBtnClickListener: () -> Unit)
    {
        this.onBackBtnClickListener = onBackBtnClickListener
    }

    fun setOnActionClickListener(onActionClickListener: () -> Unit)
    {
        this.onActionClickListener = onActionClickListener
    }

    fun setBackBtnVisible(visible: Boolean)
    {
        backBtn.isVisible = visible
    }

    fun setTitleText(text: String)
    {
        titleTextView.text = text
    }

    fun setActionText(text: String)
    {
        actionTextView.text = text
    }
}