package com.mumsapp.android.ui.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import com.mumsapp.android.R
import android.graphics.drawable.BitmapDrawable


class BaseButton : AppCompatButton {

    private var leftDrawable: Bitmap? = null
    private var leftDrawableWidth: Int? = null
    private var leftDrawableHeight: Int? = null
    private var paint: Paint? = null
    private var rect: Rect? = null
    private var drawablePadding: Float = 0f

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
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(R.styleable.BaseButton)

        val drawableLeft = array.getDrawable(R.styleable.BaseButton_alignedDrawableLeft)
        val drawablePadding = array.getDimension(R.styleable.BaseButton_alignedDrawablePadding, 0f)

        if (drawableLeft != null) {
            setAlignedDrawableLeft(drawableLeft, drawablePadding)
        }

        array.recycle()
    }

    private fun setAlignedDrawableLeft(drawableLeft: Drawable, drawablePadding: Float) {
        leftDrawable = prepareBitmap(drawableLeft)
        leftDrawableWidth = leftDrawable!!.width
        leftDrawableHeight = leftDrawable!!.height
        this.drawablePadding = drawablePadding
        paint = Paint()
        rect = Rect(0, 0, leftDrawable!!.width, leftDrawable!!.height)
    }

    private fun prepareBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(leftDrawable != null) {
            val textWidth = getPaint().measureText(text as String)
            val left = ((width / 2f) - (textWidth / 2f) - leftDrawableWidth!! - drawablePadding).toInt()
            val top = height / 2 - leftDrawableWidth!! / 2

            val destRect = Rect(left, top, left + leftDrawableWidth!!, top + leftDrawableHeight!!)
            canvas?.drawBitmap(leftDrawable, rect, destRect, paint)
        }

        canvas?.restore()
    }
}