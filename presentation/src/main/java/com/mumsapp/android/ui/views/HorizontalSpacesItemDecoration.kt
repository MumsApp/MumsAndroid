package com.mumsapp.android.ui.views

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class HorizontalSpacesItemDecoration : RecyclerView.ItemDecoration {

    private val space: Int

    constructor(space: Int) {
        this.space = space
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)

        outRect.top = space
        outRect.right = space
        outRect.bottom = space

        if (position == 0) {
            outRect.left = space
        }
    }
}