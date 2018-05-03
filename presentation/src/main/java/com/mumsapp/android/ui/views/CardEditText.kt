package com.mumsapp.android.ui.views

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.mumsapp.android.R

class CardEditText : CardView {

    lateinit var editText: BaseEditText

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
        val view = View.inflate(context, R.layout.view_card_edit_text, this) as ViewGroup
        editText = view.getChildAt(0) as BaseEditText
        editText.id = View.generateViewId()
        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.InputValues)

        val text = array.getString(R.styleable.InputValues_android_text)
        setText(text)

        val hint = array.getString(R.styleable.InputValues_android_hint)
        setHint(hint)

        array.recycle()
    }

    fun setText(text: String?) {
        editText.setText(text)
    }

    fun getText() = editText.text

    fun setHint(text: String?) {
        editText.hint = text
    }

    fun getHint() = editText.hint
}