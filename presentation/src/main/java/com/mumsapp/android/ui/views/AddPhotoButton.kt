package com.mumsapp.android.ui.views

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R

class AddPhotoButton : CardView {

    @BindView(R.id.add_photo_button_add_image_text)
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
        val view = View.inflate(context, R.layout.view_add_photo_button, this) as ViewGroup
        ButterKnife.bind(this, view)
        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.TextValues)

        val text = array.getString(R.styleable.TextValues_android_text)

        if(text != null) {
            setText(text)
        }

        array.recycle()
    }

    fun setText(text: String?) {
        textView.text = text
    }
}