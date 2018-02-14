package com.mumsapp.android.authentication

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.facebook.CallbackManager
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragmentActivity
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.base.BaseView
import com.mumsapp.android.common.features.HasOverlays
import com.mumsapp.android.ui.views.NonCLickableFrameLayout
import javax.inject.Inject

class AuthActivity : BaseFragmentActivity(), AuthView, HasOverlays {

    @Inject
    lateinit var presenter: AuthPresenter

    @Inject
    lateinit var callbackManager: CallbackManager

    @BindView(R.id.auth_progress_layout)
    lateinit var progressLayout: NonCLickableFrameLayout

    private var loadingPresented = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        ButterKnife.bind(this)
        presenter.attachViewWithLifecycle(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun makeInject() = activityComponent.inject(this)

    override fun <T: BasePresenter<BaseView>> getPresenter(): T = presenter as T

    override fun getFragmentContainerId(): Int {
        return R.id.auth_frame_layout
    }

    override fun getProperFragmentManager(): FragmentManager {
        return supportFragmentManager
    }

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
}
