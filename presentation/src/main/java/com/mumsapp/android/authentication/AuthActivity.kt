package com.mumsapp.android.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragmentActivity
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.common.features.HasOverlays
import com.mumsapp.android.main.MainActivity
import com.mumsapp.android.ui.views.NonCLickableFrameLayout
import com.mumsapp.data.google.GoogleCallbackHandler
import javax.inject.Inject

class AuthActivity : BaseFragmentActivity(), AuthView, HasOverlays {

    @Inject
    lateinit var presenter: AuthPresenter

    @Inject
    lateinit var callbackManager: CallbackManager

    @Inject
    lateinit var googleCallbackHandler: GoogleCallbackHandler

    @BindView(R.id.auth_progress_layout)
    lateinit var progressLayout: NonCLickableFrameLayout

    private var loadingPresented = false

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, AuthActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        ButterKnife.bind(this)
        presenter.attachViewWithLifecycle(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GoogleCallbackHandler.GOOGLE_SIGN_IN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            googleCallbackHandler.sendResult(task)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun makeInject() = activityComponent.inject(this)

    override fun <T: LifecyclePresenter<LifecycleView>> getPresenter(): T = presenter as T

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
