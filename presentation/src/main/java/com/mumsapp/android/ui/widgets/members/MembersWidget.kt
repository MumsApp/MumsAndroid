package com.mumsapp.android.ui.widgets.members

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.common.features.HasComponent
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.BaseButton
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.ui.views.GridRecyclerView
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import javax.inject.Inject

class MembersWidget : CardView {

    @BindView(R.id.members_title)
    lateinit var titleView: BaseTextView

    @BindView(R.id.members_recycler_view)
    lateinit var recyclerView: GridRecyclerView

    @BindView(R.id.members_button)
    lateinit var button: BaseButton

    @Inject
    lateinit var adapter: MembersWidgetAdapter

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
        val view = View.inflate(context, R.layout.widget_members, this)
        ButterKnife.bind(view)

        if(context is HasComponent<*>) {
            (context as HasComponent<ActivityComponent>).getComponent().inject(this)
        }

        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.TextValues)

        val title = array.getString(R.styleable.TextValues_title)
        showTitle(title)

        array.recycle()
    }

    fun showTitle(title: String?) {
        if(title == null) {
            titleView.visibility = View.GONE
        } else {
            titleView.visibility = View.VISIBLE
            titleView.text = title
        }
    }

    fun setButtonClickListener(listener: () -> Unit) {
        button.setOnClickListener { listener.invoke() }
    }

    fun setMembers(members: List<TemplateChatRecipient>) {
        if(recyclerView.adapter == null) {
            recyclerView.adapter = adapter
        }

        adapter.items = members
        adapter.notifyDataSetChanged()
    }
}