package com.mumsapp.android.ui.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

open class BaseRecyclerView : RecyclerView {

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
        isNestedScrollingEnabled = false
    }
}