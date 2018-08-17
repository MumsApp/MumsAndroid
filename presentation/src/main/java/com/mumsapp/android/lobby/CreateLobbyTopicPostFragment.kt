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
import com.mumsapp.android.ui.views.CardEditText
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.domain.utils.LOBBY_ROOM_KEY
import com.mumsapp.domain.utils.LOBBY_ROOM_TOPIC_KEY
import com.mumsapp.domain.model.lobby.LobbyRoom
import com.mumsapp.domain.model.lobby.LobbyRoomTopic
import javax.inject.Inject

class CreateLobbyTopicPostFragment : BaseFragment(), CreateLobbyTopicPostView {

    @Inject
    lateinit var presenter: CreateLobbyTopicPostPresenter

    @BindView(R.id.create_lobby_topic_post_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.create_lobby_topic_post_content_input)
    lateinit var contentInput: CardEditText

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(lobbyRoom: LobbyRoom, lobbyRoomTopic: LobbyRoomTopic): CreateLobbyTopicPostFragment {
            val fragment = CreateLobbyTopicPostFragment()
            fragment.arguments = createArgBundle(lobbyRoom, lobbyRoomTopic)

            return fragment
        }

        private fun createArgBundle(lobbyRoom: LobbyRoom, lobbyRoomTopic: LobbyRoomTopic): Bundle {
            val args = Bundle()
            args.putSerializable(LOBBY_ROOM_KEY, lobbyRoom)
            args.putSerializable(LOBBY_ROOM_TOPIC_KEY, lobbyRoomTopic)

            return args
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_create_lobby_topic_post, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passArgumentsToPresenter()
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
        topBar.setRightTextClickListener { presenter.onDoneClick(contentInput.getText().toString()) }
    }

    private fun passArgumentsToPresenter() {
        val lobbyRoom = arguments!!.getSerializable(LOBBY_ROOM_KEY) as LobbyRoom
        val lobbyRoomTopic = arguments!!.getSerializable(LOBBY_ROOM_TOPIC_KEY) as LobbyRoomTopic
        presenter.setArguments(lobbyRoom, lobbyRoomTopic)
    }
}