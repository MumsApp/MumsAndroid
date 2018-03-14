package com.mumsapp.android.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragmentActivity
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.common.features.HasOverlays
import com.mumsapp.android.ui.views.NonCLickableFrameLayout
import javax.inject.Inject

class MainActivity : BaseFragmentActivity(), MainView, HasOverlays {

    @Inject
    lateinit var lifecyclePresenter: MainPresenter

    @BindView(R.id.main_menu)
    lateinit var menuLayout: ConstraintLayout

    @BindView(R.id.main_progress_layout)
    lateinit var progressLayout: NonCLickableFrameLayout

    @BindView(R.id.auth_session_expired)
    lateinit var sessionExpiredView: ConstraintLayout

    private var loadingPresented = false

    private var sessionExpiredPresented = false

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        lifecyclePresenter.attachViewWithLifecycle(this)
        showMenu()
    }

    override fun getFragmentContainerId(): Int = R.id.main_frame_layout

    override fun getProperFragmentManager(): FragmentManager = supportFragmentManager

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter(): T = lifecyclePresenter as T

    override fun makeInject() = activityComponent.inject(this)

    override fun onBackPressed() {
        if (lifecyclePresenter.handleBackOrDelegateToSystem()) {
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
        sessionExpiredView.visibility = View.GONE
        loadingPresented = false
        sessionExpiredPresented = false
    }

    override fun isLoadingPresented(): Boolean = loadingPresented

    override fun isSessionExpiredPresented() = sessionExpiredPresented

    override fun showSessionExpired() {
        sessionExpiredPresented = true
        keyboardHelper.hideKeyboard(sessionExpiredView)
        progressLayout.visibility = View.GONE
        loadingPresented = false
        sessionExpiredView.visibility = View.VISIBLE
    }

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

    @OnClick(R.id.auth_session_expired_button)
    fun onSessionExpiredButtonClick() {
        lifecyclePresenter.onSessionExpiredButtonClick()
    }

    @OnClick(R.id.menu_open)
    fun onOpenMenuClick() {
        lifecyclePresenter.onOpenMenuClick()
    }

    @OnClick(R.id.menu_close)
    fun onCloseMenuClick() {
        lifecyclePresenter.onCloseMenuClick()
    }

    @OnClick(R.id.menu_where_find, R.id.menu_where_find_text)
    fun onWhereFindClick() {
        lifecyclePresenter.onWhereFindClick()
    }

    @OnClick(R.id.menu_lobby, R.id.menu_lobby_text)
    fun onLobbyClick() {
        lifecyclePresenter.onLobbyClick()
    }

    @OnClick(R.id.menu_talk, R.id.menu_talk_text)
    fun onTalkClick() {
        lifecyclePresenter.onTalkClick()
    }

    @OnClick(R.id.menu_me, R.id.menu_me_text)
    fun onMeClick() {
        lifecyclePresenter.onMeClick()
    }

    @OnClick(R.id.menu_shop, R.id.menu_shop_text)
    fun onShopClick() {
        lifecyclePresenter.onShopClick()
    }

    @OnClick(R.id.menu_offers, R.id.menu_offers_text)
    fun onOffersClick() {
        lifecyclePresenter.onOffersClick()
    }
}