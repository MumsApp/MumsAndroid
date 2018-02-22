package com.mumsapp.android.ui.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R

class TopBar: ConstraintLayout {

    @BindView(R.id.top_bar_left_button)
    lateinit var backButton: BaseImageButton

    @BindView(R.id.top_bar_title)
    lateinit var title: BaseTextView

    @BindView(R.id.top_bar_right_button)
    lateinit var rightButton: BaseImageButton

    constructor(context: Context) : super(context) {
        setup(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setup(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup(context, attrs)
    }

    private fun setup(context: Context, attrs: AttributeSet?) {
        val view = View.inflate(context, R.layout.item_top_bar, this)
        ButterKnife.bind(view)
        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.TopBar)

        val leftButtonVisibility = array.getBoolean(R.styleable.TopBar_topBarLeftButtonVisible, false)
        setLeftButtonVisibility(leftButtonVisibility)

        val leftButtonDrawable = array.getDrawable(R.styleable.TopBar_topBarLeftIcon)
        setLeftButtonDrawable(leftButtonDrawable)

        val title = array.getString(R.styleable.TopBar_topBarTitle)
        setTitleText(title)

        val rightButtonVisibility = array.getBoolean(R.styleable.TopBar_topBarRightButtonVisible, false)
        setRightButtonVisibility(rightButtonVisibility)

        val rightButtonDrawable = array.getDrawable(R.styleable.TopBar_topBarRightIcon)
        setRightButtonDrawable(rightButtonDrawable)

        array.recycle()
    }

    fun setLeftButtonVisibility(visible: Boolean) {
        setVisibilityFromBoolean(visible, backButton)
    }

    fun setLeftButtonDrawable(drawable: Drawable?) {
        rightButton.setImageDrawable(drawable)
    }

    fun setLeftButtonClickListener(listener: View.OnClickListener) {
        backButton.setOnClickListener(listener)
    }

    fun setTitleText(titleText: String) {
        title.text = titleText
    }

    fun setRightButtonVisibility(visibility: Boolean) {
        setVisibilityFromBoolean(visibility, rightButton)
    }

    fun setRightButtonDrawable(drawable: Drawable?) {
        rightButton.setImageDrawable(drawable)
    }

    fun setRightButtonClickListener(listener: View.OnClickListener) {
        rightButton.setOnClickListener(listener)
    }

    private fun setVisibilityFromBoolean(value: Boolean, view: View) {
        var visibility =  if (value) View.VISIBLE else View.INVISIBLE
        view.visibility = visibility
    }
}