package com.mumsapp.android.main

import android.animation.Animator
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragmentActivity
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.base.BaseView
import com.mumsapp.android.common.features.HasOverlays
import com.mumsapp.android.ui.views.NonCLickableFrameLayout
import javax.inject.Inject

class MainActivity : BaseFragmentActivity(), MainView, HasOverlays {

    @Inject
    lateinit var presenter: MainPresenter

    @BindView(R.id.main_menu)
    lateinit var menuLayout: ConstraintLayout

    @BindView(R.id.main_progress_layout)
    lateinit var progressLayout: NonCLickableFrameLayout

    private var loadingPresented = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        presenter.attachViewWithLifecycle(this)
        showMenu()
    }

    override fun getFragmentContainerId(): Int = R.id.main_frame_layout

    override fun getProperFragmentManager(): FragmentManager = supportFragmentManager

    override fun <T : BasePresenter<BaseView>> getPresenter(): T = presenter as T

    override fun makeInject() = activityComponent.inject(this)

    override fun onBackPressed() {
        if(presenter.handleBackOrDelegateToSystem()) {
            super.onBackPressed()
        }
    }


    override fun showLoading() {
        progressLayout.visibility = View.VISIBLE
        keyboardHelper.hideKeyboard(progressLayout)
        loadingPresented = true
    }

    override fun hideOverlays() {
        progressLayout.visibility = View.GONE
        loadingPresented = false
    }

    override fun isLoadingPresented(): Boolean = loadingPresented

    override fun showMenu() {
        menuLayout.translationY = menuLayout.height.toFloat()
        menuLayout.visibility = View.VISIBLE
        menuLayout.animate().translationY(0f).withEndAction({
            menuLayout.translationY = 0f
        })
    }

    override fun hideMenu() {
        menuLayout.animate().translationY(menuLayout.height.toFloat()).withEndAction({
            menuLayout.visibility = View.GONE
        })
    }

    @OnClick(R.id.menu_open)
    fun onOpenMenuClick() {
        presenter.onOpenMenuClick()
    }

    @OnClick(R.id.menu_close)
    fun onCloseMenuClick() {
        presenter.onCloseMenuClick()
    }
}