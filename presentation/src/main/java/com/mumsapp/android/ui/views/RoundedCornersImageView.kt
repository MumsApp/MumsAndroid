package com.mumsapp.android.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.ImageView
import com.mumsapp.android.R

class RoundedCornersImageView : ImageView {

    private var radius = 18f
    private var path: Path? = null
    private var rect: RectF? = null

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
        setupAttributes(context, attrs)
        path = Path()
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RoundedCornersImageView)

        radius = array.getFloat(R.styleable.RoundedCornersImageView_cornerRadius, radius)
        array.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        path?.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas?.clipPath(path)
        super.onDraw(canvas)
    }
}