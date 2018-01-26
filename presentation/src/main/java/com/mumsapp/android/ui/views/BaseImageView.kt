package com.mumsapp.android.ui.views

import android.content.Context
import android.support.v4.widget.ImageViewCompat
import android.util.AttributeSet
import android.widget.ImageView

class BaseImageView : ImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}