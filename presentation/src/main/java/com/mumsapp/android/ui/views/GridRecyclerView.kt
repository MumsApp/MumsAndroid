package com.mumsapp.android.ui.views

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import com.mumsapp.android.R

class GridRecyclerView : BaseRecyclerView {

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
        val array = context.obtainStyledAttributes(attrs, R.styleable.GridRecyclerView)

        val columnsCount = array.getInteger(R.styleable.GridRecyclerView_columnsCount, 2)
        setColumnsCount(columnsCount)

        array.recycle()
    }

    fun setColumnsCount(columnsCount: Int) {
        val manager = GridLayoutManager(context, columnsCount)
        layoutManager = manager

        setSpaceDivider(columnsCount)
    }

    fun setSpaceDivider(columnsCount: Int) {
        val space = context.resources.getDimension(R.dimen.padding_12dp).toInt()
        val divider = VerticalSpacesItemDecoration(space, columnsCount)
        addItemDecoration(divider)
    }
}