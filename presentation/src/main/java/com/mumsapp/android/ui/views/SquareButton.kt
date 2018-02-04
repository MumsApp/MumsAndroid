package com.mumsapp.android.ui.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R

class SquareButton: CardView {

    @BindView(R.id.square_button_image)
    lateinit var imageView: BaseImageView

    @BindView(R.id.square_button_text)
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
        val view = View.inflate(context, R.layout.item_square_button, this)
        ButterKnife.bind(view)
        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val buttonArray = context.obtainStyledAttributes(attrs, R.styleable.SquareButton)
        val textArray = context.obtainStyledAttributes(attrs, R.styleable.TextAttributes)

        val image = buttonArray.getDrawable(R.styleable.SquareButton_image)
        setImage(image)

        val text = textArray.getString(R.styleable.TextAttributes_text)
        setText(text)

        buttonArray.recycle()
        textArray.recycle()
    }

    fun setImage(image: Drawable) {
        imageView.setImageDrawable(image)
    }

    fun setText(text: String) {
        textView.setText(text)
    }
}