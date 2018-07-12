package com.mumsapp.android.ui.widgets.children_selection

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.util.ChildrenMapper
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.model.user.UserResponse.Child
import java.lang.ref.WeakReference

class ChildrenSelectionWidgetViewHolder : BaseViewHolder<Child> {

    private val childrenMapper: ChildrenMapper
    private var child: Child? = null

    @BindView(R.id.cell_children_selection_name)
    lateinit var nameView: BaseTextView

    private var listener: WeakReference<(item: UserResponse.Child) -> Unit>? = null

    constructor(childrenMapper: ChildrenMapper, view: View) : super(view) {
        this.childrenMapper = childrenMapper
        ButterKnife.bind(this, itemView)
    }

    override fun init(item: Child) {
        child = item
        nameView.text = childrenMapper.getReadableName(item)
    }

    fun setEditClickListener(listener: (item: UserResponse.Child) -> Unit) {
        this.listener = WeakReference(listener)
    }

    @OnClick(R.id.cell_children_selection_edit)
    fun onEditClick() {
        listener?.get()?.invoke(child!!)
    }
}