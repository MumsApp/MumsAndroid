package com.mumsapp.android.chat

import android.content.Context
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseDialog
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.base.BaseView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.StripeDividerRecyclerView
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import javax.inject.Inject

class ChatSettingsDialog(context: Context) : BaseDialog(context), ChatSettingsView {

    @Inject
    lateinit var presenter: ChatSettingsPresenter

    @Inject
    lateinit var adapter: ChatSettingsAdapter

    @BindView(R.id.chat_settings_recycler_view)
    lateinit var recyclerView: StripeDividerRecyclerView

    override fun <T : BasePresenter<BaseView>> getPresenter() = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        getComponent(ActivityComponent::class.java)?.inject(this)
        setContentView(R.layout.dialog_chat_settings)
        configureWindow()
        ButterKnife.bind(this)
        presenter.attachView(this)
        presenter.start()
    }

    @OnClick(R.id.chat_settings_close_button)
    fun onCloseClick() {
        presenter.onCloseClick()
    }

    override fun dismissView() {
        dismiss()
    }

    override fun showItems(items: List<TemplateChatRecipient>) {
        adapter.items = items
        adapter.notifyDataSetChanged()

        if(recyclerView.adapter == null) {
            recyclerView.adapter = adapter
        }
    }
}