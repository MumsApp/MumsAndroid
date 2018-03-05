package com.mumsapp.android.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.LifecycleFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.BaseButton
import javax.inject.Inject

class AuthMenuFragment: LifecycleFragment(), AuthMenuView {

    @Inject
    lateinit var presenter: AuthMenuPresenter

    @BindView(R.id.auth_menu_sign_up)
    lateinit var button: BaseButton

    companion object {
        fun getInstance(): AuthMenuFragment {
            return AuthMenuFragment()
        }
    }

    override fun <T : LifecyclePresenter<LifecycleView>> getPresenter(): T = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_auth_menu, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
    }

    @OnClick(R.id.auth_menu_sign_up)
    fun onSignUpClick() {
        presenter.onSignUpClick()
    }

    @OnClick(R.id.auth_menu_sign_in)
    fun onSignInClick() {
        presenter.onSignInClick()
    }

    @OnClick(R.id.auth_menu_create_page)
    fun onCreatePageClick() {
        presenter.onCreatePageClick()
    }
}