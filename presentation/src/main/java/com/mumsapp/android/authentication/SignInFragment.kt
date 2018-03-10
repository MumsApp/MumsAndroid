package com.mumsapp.android.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.BaseInput
import com.mumsapp.android.ui.views.TopBar
import javax.inject.Inject

class SignInFragment: BaseFragment(), SignInView {

    @Inject
    lateinit var presenter: SignInPresenter

    @BindView(R.id.sign_in_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.sign_in_email)
    lateinit var emailInput: BaseInput

    @BindView(R.id.sign_in_password)
    lateinit var passwordInput: BaseInput

    companion object {
        fun getInstance(): SignInFragment {
            return SignInFragment()
        }
    }

    override fun <T : LifecyclePresenter<LifecycleView>> getPresenter(): T = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_sign_in, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener(View.OnClickListener { presenter.onBackClick() })
    }

    @OnClick(R.id.sign_in_facebook)
    fun onFacebookClick() {
        presenter.onFacebookClick()
    }

    @OnClick(R.id.sign_in_google)
    fun onGoogleClick() {
        presenter.onGoogleClick()
    }

    @OnClick(R.id.sign_in_button)
    fun onButtonClick() {
        val email = emailInput.text
        val password = passwordInput.text

        presenter.onSignInClick(email, password)
    }

    @OnClick(R.id.sign_in_no_account)
    fun onSignUpClick() {
        presenter.onSignUpClick()
    }

    @OnClick(R.id.sign_in_forget_password)
    fun onForgetPasswordClick() {
        presenter.onForgetPasswordClick()
    }

    override fun showEmailError(error: String) {
        emailInput.error = error
    }

    override fun showPasswordError(error: String) {
        passwordInput.error = error
    }

    override fun clearErrors() {
        emailInput.clearError()
        passwordInput.clearError()
    }
}