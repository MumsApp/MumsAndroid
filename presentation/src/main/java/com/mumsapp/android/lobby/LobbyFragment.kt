package com.mumsapp.android.lobby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.CardsRecyclerView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.domain.model.lobby.LobbyItem
import javax.inject.Inject

class LobbyFragment : BaseFragment(), LobbyView {

    @Inject
    lateinit var presenter: LobbyPresenter

    @Inject
    lateinit var adapter: LobbyItemsAdapter

    @BindView(R.id.lobby_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.lobby_recycler_view)
    lateinit var recyclerView: CardsRecyclerView

    override fun <T : LifecyclePresenter<LifecycleView>> getPresenter() = presenter as T

    companion object {
        fun getInstance(): LobbyFragment {
            return LobbyFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_lobby, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
        topBar.setRightButtonClickListener { presenter.onFiltersButtonClick() }
        topBar.setSearchListener(presenter::onSearch)
    }

    @OnClick(R.id.lobby_add_category)
    fun onAddCategoryClick() {
        presenter.onAddCategoryClick()
    }

    override fun showItems(items: List<LobbyItem>, listener: (item: LobbyItem, value: Boolean) -> Unit) {
        adapter.items = items
        adapter.notifyDataSetChanged()

        if(recyclerView.adapter == null) {
            adapter.listener = listener
            recyclerView.adapter = adapter
        }
    }
}