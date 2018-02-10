package com.mumsapp.android.authentication

import com.mumsapp.android.R
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.user.SignInUseCase
import com.mumsapp.domain.model.user.SignInRequest
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.ValidationHelper
import javax.inject.Inject

class SignInPresenter : BasePresenter<SignInView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val validationHelper: ValidationHelper
    private val resourceRepository: ResourceRepository
    private val signInUseCase: SignInUseCase

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                validationHelper: ValidationHelper,
                resourceRepository: ResourceRepository, signInUseCase: SignInUseCase) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.validationHelper = validationHelper
        this.resourceRepository = resourceRepository
        this.signInUseCase = signInUseCase
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onSignInClick(email: String, password: String) {
        view?.clearErrors()

        if(validateFields(email, password)) {
            val request = SignInRequest(email, password)

            signIn(request)
        }
    }

    private fun validateFields(email: String, password: String): Boolean {
        var isValid = false

        if(!validationHelper.checkEmailValid(email)) {
            view?.showEmailError(resourceRepository.getString(R.string.email_error))
            isValid = false
        }

        if(!validationHelper.checkIsNotEmpty(password)) {
            view?.showPasswordError(resourceRepository.getString(R.string.empty_password_error))
            isValid = false
        }

        return isValid
    }

    private fun signIn(request: SignInRequest) {
        addDisposable(
                signInUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleSignInSuccess, this::handleSignInError)
        )
    }

    private fun handleSignInSuccess(user: UserResponse) {

    }

    private fun handleSignInError(throwable: Throwable) {
        view?.showSnackbar(throwable.message!!)
    }

    fun onSignUpClick() {
        fragmentsNavigationService.popFragment()
        fragmentsNavigationService.openSignUpFragment(true)
    }

    fun onForgetPasswordClick() {
        fragmentsNavigationService.openForgetPasswordFragment(true)
    }
}