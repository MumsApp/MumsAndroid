package com.mumsapp.android.authentication

import com.mumsapp.android.R
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.user.signUp.SignUpUseCase
import com.mumsapp.domain.model.EmptyResponse
import com.mumsapp.domain.model.user.SignUpRequest
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.ValidationHelper
import javax.inject.Inject

class SignUpPresenter: BasePresenter<SignUpView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val signUpUseCase: SignUpUseCase
    private val validationHelper: ValidationHelper
    private val resourceRepository: ResourceRepository

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                signUpUseCase: SignUpUseCase, validationHelper: ValidationHelper,
                resourceRepository: ResourceRepository) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.signUpUseCase = signUpUseCase
        this.validationHelper = validationHelper
        this.resourceRepository = resourceRepository
    }

    fun onSignUpClick(firstName: String, lastName: String, email: String, password: String,
                      passwordConfirmation: String) {

        if(validateFields(firstName, lastName, email, password, passwordConfirmation)) {
            var request = SignUpRequest(firstName, lastName, email, password)

            signUp(request)
        }
    }

    private fun validateFields(firstName: String, lastName: String, email: String, password: String,
                               passwordConfirmation: String): Boolean {
        var isValid = true

        if(!validationHelper.checkNameValid(firstName)) {
            view?.showFirstNameError(resourceRepository.getString(R.string.first_name_error))
            isValid = false
        }

        if(!validationHelper.checkNameValid(lastName)) {
            view?.showLastNameError(resourceRepository.getString(R.string.last_name_error))
            isValid = false
        }

        if(!validationHelper.checkEmailValid(email)) {
            view?.showEmailError(resourceRepository.getString(R.string.email_error))
            isValid = false
        }

        if(!validationHelper.checkIsEmpty(password)) {
            view?.showPasswordError(resourceRepository.getString(R.string.empty_password_error))
            isValid = false
        }

        if(!validationHelper.checkPasswordValid(password)) {
            view?.showPasswordError(resourceRepository.getString(R.string.weak_password_error))
            isValid = false
        }

        if(!validationHelper.areFieldsTheSame(password, passwordConfirmation)) {
            view?.showPasswordConfirmationError(resourceRepository.getString(R.string.password_confirmation_error))
            isValid = false
        }

        return isValid
    }

    private fun signUp(request: SignUpRequest) {
        addDisposable(
                signUpUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleRegisterSuccess, this::handleRegisterError)
        )
    }

    private fun handleRegisterSuccess(response: EmptyResponse) {

    }

    private fun handleRegisterError(throwable: Throwable) {

    }
}