package com.mumsapp.android.ui.views

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import com.mumsapp.android.R

class HorizontalRecyclerView : BaseRecyclerView {

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
        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        layoutManager = manager

        setupDivider(context)
    }

    private fun setupDivider(context: Context) {
        val space = context.resources.getDimension(R.dimen.padding_12dp).toInt()
        val divider = HorizontalSpacesItemDecoration(space)
        addItemDecoration(divider)
    }
}