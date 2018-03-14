package com.mumsapp.android.authentication

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
import javax.inject.Inject

class CreatePageFragment: BaseFragment(), CreatePageView {

    @Inject
    lateinit var lifecyclePresenter: CreatePagePresenter

    @BindView(R.id.create_page_top_bar)
    lateinit var topBar: TopBar

    companion object {
        fun getInstance(): CreatePageFragment {
            return CreatePageFragment()
        }
    }

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter(): T = lifecyclePresenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_create_page, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecyclePresenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { lifecyclePresenter.onBackClick() }
    }
}