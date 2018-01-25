package com.mumsapp.android.authentication

import android.os.Bundle
import android.support.v4.app.FragmentManager
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragmentActivity
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.base.BaseView
import javax.inject.Inject

class AuthActivity : BaseFragmentActivity(), AuthView {

    @Inject
    lateinit var presenter: AuthPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        ButterKnife.bind(this)
        presenter.attachViewWithLifecycle(this)
    }

    override fun makeInject() = activityComponent.inject(this)

    override fun <T: BasePresenter<BaseView>> getPresenter(): T = presenter as T

    override fun getFragmentContainerId(): Int {
        return R.id.main_frame_layout
    }

    override fun getProperFragmentManager(): FragmentManager {
        return supportFragmentManager
    }
}
