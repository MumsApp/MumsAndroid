package com.mumsapp.android.ui.widgets.children_selection

import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.android.util.ChildrenMapper
import com.mumsapp.domain.model.user.UserResponse
import javax.inject.Inject

class ChildrenSelectionWidgetAdapter : BaseRecyclerViewAdapter<UserResponse.Child, ChildrenSelectionWidgetViewHolder> {

    private val childrenMapper: ChildrenMapper

    var editClickListener: ((item: UserResponse.Child) -> Unit)? = null

    @Inject
    constructor(childrenMapper: ChildrenMapper) : super() {
        this.childrenMapper = childrenMapper
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildrenSelectionWidgetViewHolder {
        val itemView = inflate(parent, R.layout.cell_children_selection_widget)
        return ChildrenSelectionWidgetViewHolder(childrenMapper, itemView)
    }

    override fun onBindViewHolder(holder: ChildrenSelectionWidgetViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        if(editClickListener != null) {
            holder.setEditClickListener(editClickListener!!)
        }
    }
}