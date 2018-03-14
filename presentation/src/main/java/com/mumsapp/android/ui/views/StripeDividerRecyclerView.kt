package com.mumsapp.android.ui.views

import android.content.Context
import android.content.res.Resources
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.util.AttributeSet
import com.mumsapp.android.R

class StripeDividerRecyclerView : BaseRecyclerView {

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
        val drawable = ContextCompat.getDrawable(context, R.drawable.default_divider)
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(drawable!!)
        addItemDecoration(divider)
    }
}