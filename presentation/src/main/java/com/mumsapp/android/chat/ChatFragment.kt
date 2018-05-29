package com.mumsapp.android.chat

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.common.adapters.ViewPagerAdapter
import com.mumsapp.android.di.components.ActivityComponent
import javax.inject.Inject

class ChatFragment : BaseFragment(), ChatView {

    @Inject
    lateinit var presenter: ChatPresenter

    @BindView(R.id.fragment_chat_tab_layout)
    lateinit var tabLayout: TabLayout

    @BindView(R.id.fragment_chat_viewpager)
    lateinit var viewPager: ViewPager

    private var adapter: ViewPagerAdapter? = null

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance() : ChatFragment {
            return ChatFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_chat, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
    }

    override fun setItems(fragments: List<BaseFragment>, titles: List<String>) {
        if(adapter == null) {
            adapter = ViewPagerAdapter(safeChildFragmentManager())
        }

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        adapter!!.setItems(fragments, titles)
    }
}