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
import com.mumsapp.android.ui.views.TopBar
import javax.inject.Inject

class ChatListFragment : BaseFragment(), ChatListView {

    @Inject
    lateinit var presenter: ChatListPresenter

    @Inject
    lateinit var dialogsProvider: DialogsProvider

    @BindView(R.id.chat_list_top_bar)
    lateinit var topBar: TopBar

    private var chatSettingsDialog: ChatSettingsDialog? = null

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance() : ChatListFragment {
            return ChatListFragment()
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
        topBar.setLeftButtonClickListener { presenter.onFiltersButtonClick() }
        topBar.setRightButtonClickListener { presenter.onSettingsButtonClick() }
        topBar.setSearchListener(presenter::onSearch)
    }

    override fun openChatSettingsDialog() {
        if(chatSettingsDialog == null) {
            chatSettingsDialog = dialogsProvider.createChatSettingsDialog()
        }

        chatSettingsDialog?.show()
    }
}