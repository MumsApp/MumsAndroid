package com.mumsapp.android.ui.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.mumsapp.android.R

class CardsTextView : CardView {

    lateinit var textView: BaseTextView

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
        val view = View.inflate(context, R.layout.view_card_text_view, this) as ViewGroup
        textView = view.getChildAt(0) as BaseTextView
        textView.id = View.generateViewId()
        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.TextValues)

        val text = array.getString(R.styleable.TextValues_android_text)
        setText(text)

        val textColor = array.getColor(R.styleable.TextValues_android_textColor, resources.getColor(R.color.black))
        setTextColor(textColor)

        val textSize = array.getDimension(R.styleable.TextValues_android_textSize, resources.getDimension(R.dimen.text_size_normal))
        setTextSize(textSize)

        array.recycle()
    }

    fun setText(text: String?) {
        textView.setText(text)
    }

    fun getTextColor() = textView.text

    fun setTextColor(color: Int) {
        textView.setTextColor(color)
    }

    fun getTextSize() = textView.text

    fun setTextSize(textSize: Float) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
    }

    fun getText() = textView.text
}