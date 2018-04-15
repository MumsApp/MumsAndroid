package com.mumsapp.android.mums_app_offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.chat.ChatListAdapter
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.CardsRecyclerView
import com.mumsapp.domain.model.chat.TemplateChatThread
import javax.inject.Inject

class MumsAppOffersFragment : BaseFragment(), MumsAppOffersView {

    @Inject
    lateinit var presenter: MumsAppOffersPresenter

    @Inject
    lateinit var adapter: ChatListAdapter

    @BindView(R.id.mums_app_offers_recycler_view)
    lateinit var recyclerView: CardsRecyclerView

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance() = MumsAppOffersFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_mums_app_offers, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
    }

    override fun showItems(items: List<TemplateChatThread>) {
        adapter.items = items
        adapter.notifyDataSetChanged()
        adapter.setItemsClickListener(presenter::onChatThreadClick)

        if(recyclerView.adapter == null) {
            recyclerView.adapter = adapter
        }
    }
}