package com.mumsapp.android.lobby

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
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.CardsRecyclerView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.util.LOBBY_CATEGORY_ID_KEY
import com.mumsapp.domain.model.lobby.LobbyPost
import javax.inject.Inject

class LobbyCategoryDetailsFragment : BaseFragment(), LobbyCategoryDetailsView {

    @Inject
    lateinit var presenter: LobbyCategoryDetailsPresenter

    @Inject
    lateinit var adapter: LobbyPostAdapter

    @BindView(R.id.lobby_category_details_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.lobby_category_details_recycler_view)
    lateinit var recyclerView: CardsRecyclerView

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(lobbyCategoryId: Int): LobbyCategoryDetailsFragment {
            val fragment = LobbyCategoryDetailsFragment()
            fragment.arguments = createArgBundle(lobbyCategoryId)

            return fragment
        }

        private fun createArgBundle(lobbyCategoryId: Int): Bundle {
            val args = Bundle()
            args.putInt(LOBBY_CATEGORY_ID_KEY, lobbyCategoryId)

            return args
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_lobby_category_details, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passArgumentsToPresenter()
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
        topBar.setRightButtonClickListener { presenter.onCreatePostClick() }
    }

    private fun passArgumentsToPresenter() {
        val lobbyCategoryId = arguments?.getInt(LOBBY_CATEGORY_ID_KEY)
        presenter.setArguments(lobbyCategoryId)
    }

    override fun showPosts(posts: List<LobbyPost>, replyClickListener: (item: LobbyPost) -> Unit) {
        adapter.items = posts
        adapter.notifyDataSetChanged()

        if(recyclerView.adapter == null) {
            adapter.replyClickListener = replyClickListener
            recyclerView.adapter = adapter
        }
    }
}