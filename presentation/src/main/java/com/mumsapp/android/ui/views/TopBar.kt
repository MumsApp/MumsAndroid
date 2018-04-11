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

    @BindView(R.id.top_bar_title)
    lateinit var title: BaseTextView

    @BindView(R.id.top_bar_left_button)
    lateinit var leftButton: BaseImageButton

    @BindView(R.id.top_bar_right_button)
    lateinit var rightButton: BaseImageButton

    @BindView(R.id.top_bar_right_text)
    lateinit var rightText: BaseTextView

    @BindView(R.id.top_bar_search_input)
    lateinit var searchInput: BaseEditText

    @BindView(R.id.top_bar_search_button)
    lateinit var searchButton: BaseImageButton

    @BindView(R.id.top_bar_search_divider)
    lateinit var searchDivider: View

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

        val rightTextVisibility = array.getBoolean(R.styleable.TopBar_topBarRightTextVisible, false)
        setRightTextVisibility(rightTextVisibility)

        val rightText = array.getString(R.styleable.TopBar_topBarRightText)
        setRightText(rightText)

        val searchVisible = array.getBoolean(R.styleable.TopBar_searchVisible, false)
        setSearchVisibility(searchVisible)

        array.recycle()
    }

    fun setLeftButtonVisibility(visible: Boolean) {
        setVisibilityFromBoolean(visible, leftButton)
    }

    fun setLeftButtonDrawable(drawable: Drawable?) {
        leftButton.setImageDrawable(drawable)
    }

    fun setLeftButtonClickListener(listener: (view: View) -> Unit) {
        leftButton.setOnClickListener(listener)
    }

    fun setTitleText(titleText: String?) {
        title.text = titleText
    }

    fun setRightButtonVisibility(visibility: Boolean) {
        setVisibilityFromBoolean(visibility, rightButton)
    }

    fun setRightButtonDrawable(drawable: Drawable?) {
        rightButton.setImageDrawable(drawable)
    }

    fun setRightButtonClickListener(listener: (view: View) -> Unit) {
        rightButton.setOnClickListener(listener)
    }

    fun setRightTextVisibility(visibility: Boolean) {
        setVisibilityFromBoolean(visibility, rightText)
    }

    fun setRightText(text: String?) {
        rightText.text = text
    }

    fun setRightTextClickListener(listener: (view: View) -> Unit) {
        rightText.setOnClickListener(listener)
    }

    fun setSearchVisibility(visibility: Boolean) {
        setVisibilityFromBoolean(visibility, searchInput, View.GONE)
        setVisibilityFromBoolean(visibility, searchButton, View.GONE)
        setVisibilityFromBoolean(visibility, searchDivider, View.GONE)
    }

    fun setSearchListener(listener: (value: String) -> Unit) {
        searchButton.setOnClickListener({
            listener.invoke(searchInput.text.toString())
        })
    }

    fun requestSearchFocus() {
        searchInput.requestFocus()
        searchInput.requestFocusFromTouch()
    }

    private fun setVisibilityFromBoolean(value: Boolean, view: View) {
        setVisibilityFromBoolean(value, view, View.INVISIBLE)
    }

    private fun setVisibilityFromBoolean(value: Boolean, view: View, valueForFalse: Int) {
        val visibility =  if (value) View.VISIBLE else valueForFalse
        view.visibility = visibility
    }
}