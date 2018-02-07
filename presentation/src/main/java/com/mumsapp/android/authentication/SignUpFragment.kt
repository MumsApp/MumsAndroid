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
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.base.BaseView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.ui.views.BaseButton
import com.mumsapp.android.ui.views.BaseInput
import javax.inject.Inject

class SignUpFragment: BaseFragment(), SignUpView {

    @Inject
    lateinit var presenter: SignUpPresenter

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

    companion object {
        fun getInstance(): SignUpFragment {
            return SignUpFragment()
        }
    }

    override fun <T : BasePresenter<BaseView>> getPresenter(): T = presenter as T

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
    }

    @OnClick(R.id.sign_up_button)
    fun onSaveClick() {
        var firstName = firstNameInput.text
        var lastName = lastNameInput.text
        var email = emailInput.text
        var password = passwordInput.text
        var passwordConfirmation = passwordConfirmationInput.text

        presenter.onSignUpClick(firstName, lastName, email, password, passwordConfirmation)
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
}