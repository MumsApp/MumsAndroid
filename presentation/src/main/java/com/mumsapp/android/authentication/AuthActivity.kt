package com.mumsapp.android.authentication

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.widget.ContentLoadingProgressBar
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragmentActivity
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.base.BaseView
import com.mumsapp.android.common.features.HasProgress
import javax.inject.Inject

class AuthActivity : BaseFragmentActivity(), AuthView, HasProgress {

    @Inject
    lateinit var presenter: AuthPresenter

    @BindView(R.id.auth_progress)
    lateinit var progressBar: ContentLoadingProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        ButterKnife.bind(this)
        presenter.attachViewWithLifecycle(this)
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

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }
}
