package com.mumsapp.android.ui.views

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpacesItemDecoration : RecyclerView.ItemDecoration {

    private val space: Int
    private val columnsCount: Int

    constructor(space: Int, columnsCount: Int) {
        this.space = space
        this.columnsCount = columnsCount
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % columnsCount

        outRect.left = space - column * space / columnsCount
        outRect.right = (column + 1) * space / columnsCount
        outRect.bottom = space

        if (position < columnsCount) {
            outRect.top = space
        }
    }
}