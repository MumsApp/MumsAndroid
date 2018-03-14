package com.mumsapp.android.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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

class SignUpFragment: BaseFragment(), SignUpView {

    @Inject
    lateinit var presenter: SignUpPresenter

    @BindView(R.id.sign_up_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.sign_up_first_name)
    lateinit var firstNameInput: BaseInput

    @BindView(R.id.sign_up_last_name)
    lateinit var lastNameInput: BaseInput

    @BindView(R.id.sign_up_email)
    lateinit var emailInput: BaseInput

    @BindView(R.id.sign_up_password)
    lateinit var passwordInput: BaseInput

    @BindView(R.id.sign_up_confirmation)
    lateinit var passwordConfirmationInput: BaseInput

    @BindView(R.id.sign_up_terms_checkbox)
    lateinit var termsCheckbox: CheckBox

    companion object {
        fun getInstance(): SignUpFragment {
            return SignUpFragment()
        }
    }

    override fun <T : LifecyclePresenter<LifecycleView>> getPresenter(): T = presenter as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_sign_up, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
    }

    @OnClick(R.id.sign_up_facebook)
    fun onFacebookClick() {
        presenter.onFacebookClick()
    }

    @OnClick(R.id.sign_up_google)
    fun onGoogleClick() {
        presenter.onGoogleClick()
    }

    @OnClick(R.id.sign_up_terms_label)
    fun onTermsLinkClick() {
        presenter.onTermsLinkClick()
    }

    @OnClick(R.id.sign_up_button)
    fun onSaveClick() {
        val firstName = firstNameInput.text
        val lastName = lastNameInput.text
        val email = emailInput.text
        val password = passwordInput.text
        val passwordConfirmation = passwordConfirmationInput.text
        val termsChecked = termsCheckbox.isChecked

        presenter.onSignUpClick(firstName, lastName, email, password, passwordConfirmation, termsChecked)
    }

    @OnClick(R.id.sign_up_already_have_account)
    fun onSignInClick() {
        presenter.onSignInClick()
    }

    override fun showFirstNameError(error: String) {
        firstNameInput.error = error
    }

    override fun showLastNameError(error: String) {
        lastNameInput.error = error
    }

    override fun showEmailError(error: String) {
        emailInput.error = error
    }

    override fun showPasswordError(error: String) {
        passwordInput.error = error
    }

    override fun showPasswordConfirmationError(error: String) {
        passwordConfirmationInput.error = error
    }

    override fun showTermsAndConditionsError(error: String) {
        showSnackbar(error)
    }

    override fun clearErrors() {
        firstNameInput.clearError()
        lastNameInput.clearError()
        emailInput.clearError()
        passwordInput.clearError()
        passwordConfirmationInput.clearError()
    }
}