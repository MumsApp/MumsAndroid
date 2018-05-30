package com.mumsapp.android.chat

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
import com.mumsapp.android.navigation.DialogsProvider
import com.mumsapp.android.ui.views.CardsRecyclerView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.domain.model.chat.TemplateChatThread
import javax.inject.Inject

class FriendsChatFragment : BaseFragment(), ChatListView {

    @Inject
    lateinit var presenter: FriendsChatPresenter

    @Inject
    lateinit var dialogsProvider: DialogsProvider

    @Inject
    lateinit var adapter: ChatListAdapter

    @BindView(R.id.chat_list_recycler_view)
    lateinit var recyclerView: CardsRecyclerView

    private var chatSettingsDialog: ChatSettingsDialog? = null

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance() : FriendsChatFragment {
            return FriendsChatFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_chat_list, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
//        topBar.setLeftButtonClickListener { presenter.onFiltersButtonClick() }
//        topBar.setRightButtonClickListener { presenter.onSettingsButtonClick() }
//        topBar.setSearchListener(presenter::onSearch)
    }

    override fun openChatSettingsDialog() {
        if(chatSettingsDialog == null) {
            chatSettingsDialog = dialogsProvider.createChatSettingsDialog()
        }

        chatSettingsDialog?.show()
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