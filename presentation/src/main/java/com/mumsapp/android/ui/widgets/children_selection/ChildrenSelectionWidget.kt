package com.mumsapp.android.ui.widgets.children_selection

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.common.features.HasComponent
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.BaseRecyclerView
import com.mumsapp.domain.model.user.UserResponse
import javax.inject.Inject

class ChildrenSelectionWidget : CardView {

    @BindView(R.id.children_selection_widget_divider)
    lateinit var divider: View

    @BindView(R.id.children_selection_widget_recycler)
    lateinit var recycler: BaseRecyclerView

    @Inject
    lateinit var adapter: ChildrenSelectionWidgetAdapter

    var addMaleListener: (() -> Unit)? = null
    var addFemaleListener: (() -> Unit)? = null
    var addToComeListener: (() -> Unit)? = null

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
        val view = View.inflate(context, R.layout.widget_children_selection, this)
        ButterKnife.bind(view)
        configureViews()

        if(context is HasComponent<*>) {
            (context as HasComponent<ActivityComponent>).getComponent().inject(this)
        }
    }

    private fun configureViews() {
    }

    fun showChildren(items: List<UserResponse.Child>, editListener: (item: UserResponse.Child) -> Unit, longClickListener: (item: UserResponse.Child) -> Unit) {
        divider.visibility = View.VISIBLE
        recycler.visibility = View.VISIBLE

        if(recycler.adapter == null) {
            recycler.adapter = adapter
        }

        adapter.items = items
        adapter.editClickListener = editListener
        adapter.setItemsLongClickListener(longClickListener)
        adapter.notifyDataSetChanged()
    }

    fun hideChildren() {
        divider.visibility = View.GONE
        recycler.visibility = View.GONE
    }

    @OnClick(R.id.children_selection_widget_add_male_icon, R.id.children_selection_widget_add_male_text)
    fun onAddMaleClick() {
        addMaleListener?.invoke()
    }

    @OnClick(R.id.children_selection_widget_add_female_icon, R.id.children_selection_widget_add_female_text)
    fun onAddFemaleClick() {
        addFemaleListener?.invoke()
    }

    @OnClick(R.id.children_selection_widget_add_come_icon, R.id.children_selection_widget_add_come_text)
    fun onAddToComeClick() {
        addToComeListener?.invoke()
    }
}