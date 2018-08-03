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
import com.mumsapp.android.ui.widgets.PaginationWidget
import com.mumsapp.android.util.LOBBY_ROOM_KEY
import com.mumsapp.domain.model.lobby.LobbyRoom
import com.mumsapp.domain.model.lobby.LobbyRoomTopic
import javax.inject.Inject

class LobbyRoomDetailsFragment : BaseFragment(), LobbyRoomDetailsView {

    @Inject
    lateinit var presenter: LobbyRoomDetailsPresenter

    @Inject
    lateinit var adapter: LobbyRoomTopicsAdapter

    @BindView(R.id.lobby_category_details_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.lobby_category_details_recycler_view)
    lateinit var recyclerView: CardsRecyclerView

    @BindView(R.id.lobby_category_details_pagination)
    lateinit var paginationWidget: PaginationWidget

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(lobbyRoom: LobbyRoom): LobbyRoomDetailsFragment {
            val fragment = LobbyRoomDetailsFragment()
            fragment.arguments = createArgBundle(lobbyRoom)

            return fragment
        }

        private fun createArgBundle(lobbyRoom: LobbyRoom): Bundle {
            val args = Bundle()
            args.putSerializable(LOBBY_ROOM_KEY, lobbyRoom)

            return args
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_lobby_room_details, container, false)
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
        val lobbyRoom = arguments!!.getSerializable(LOBBY_ROOM_KEY) as LobbyRoom
        presenter.setArguments(lobbyRoom)
    }

    override fun setTitle(title: String) {
        topBar.setTitleText(title)
    }

    override fun showTopics(topics: List<LobbyRoomTopic>, onTopicClick: (item: LobbyRoomTopic) -> Unit,
                            replyClickListener: (item: LobbyRoomTopic) -> Unit,
                            userClickListener: (item: LobbyRoomTopic) -> Unit) {
        adapter.items = topics
        adapter.notifyDataSetChanged()

        if(recyclerView.adapter == null) {
            adapter.replyClickListener = replyClickListener
            adapter.userClickListener = userClickListener
            adapter.setItemsClickListener(onTopicClick)

            recyclerView.adapter = adapter
        }
    }

    override fun setupPagination(lastPage: Int, pageChangeListener: ((page: Int) -> Unit)?) {
        paginationWidget.setLastPage(lastPage)
        paginationWidget.pageChangeListener = pageChangeListener
    }
}