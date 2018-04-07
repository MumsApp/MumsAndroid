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
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.util.CHAT_THREAD_KEY
import com.mumsapp.domain.model.chat.TemplateChatThread
import javax.inject.Inject

class ChatThreadFragment : BaseFragment(), ChatThreadView {

    @Inject
    lateinit var presenter: ChatThreadPresenter

    @BindView(R.id.chat_thread_top_bar)
    lateinit var topBar: TopBar

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(thread: TemplateChatThread) : ChatThreadFragment {
            val args = createArgumentBundle(thread)

            val fragment = ChatThreadFragment()
            fragment.arguments = args

            return fragment
        }

        private fun createArgumentBundle(thread: TemplateChatThread) : Bundle {
            val args = Bundle()
            args.putSerializable(CHAT_THREAD_KEY, thread)

            return args
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_chat_thread, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setArgumentsToPresenter()
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
    }

    private fun setArgumentsToPresenter() {
        val thread = arguments!!.getSerializable(CHAT_THREAD_KEY) as TemplateChatThread
        presenter.setArguments(thread)
    }
}