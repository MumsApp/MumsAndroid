package com.mumsapp.android.authentication

import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.user.FacebookSignUpUseCase
import com.mumsapp.domain.interactor.user.GoogleSignUpUseCase
import com.mumsapp.domain.interactor.user.SignUpUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.user.SignUpRequest
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.SessionManager
import com.mumsapp.domain.utils.ValidationHelper
import javax.inject.Inject

class SignUpPresenter: LifecyclePresenter<SignUpView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val signUpUseCase: SignUpUseCase
    private val validationHelper: ValidationHelper
    private val resourceRepository: ResourceRepository
    private val sessionManager: SessionManager
    private val activitiesNavigationService: ActivitiesNavigationService
    private val facebookSignUpUseCase: FacebookSignUpUseCase
    private val googleSignUpUseCase: GoogleSignUpUseCase

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                signUpUseCase: SignUpUseCase, validationHelper: ValidationHelper,
                resourceRepository: ResourceRepository,
                sessionManager: SessionManager,
                activitiesNavigationService: ActivitiesNavigationService,
                facebookSignUpUseCase: FacebookSignUpUseCase,
                googleSignUpUseCase: GoogleSignUpUseCase) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.signUpUseCase = signUpUseCase
        this.validationHelper = validationHelper
        this.resourceRepository = resourceRepository
        this.sessionManager = sessionManager
        this.activitiesNavigationService = activitiesNavigationService
        this.facebookSignUpUseCase = facebookSignUpUseCase
        this.googleSignUpUseCase = googleSignUpUseCase
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onFacebookClick() {
        addDisposable(
                facebookSignUpUseCase.execute(EmptyRequest())
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleRegisterSuccess, this::handleRegisterError)
        )
    }

    fun onGoogleClick() {
        addDisposable(
                googleSignUpUseCase.execute(EmptyRequest())
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleRegisterSuccess, this::handleRegisterError)
        )
    }

    fun onTermsLinkClick() {

    }

    fun onSignUpClick(firstName: String, lastName: String, email: String, password: String,
                      passwordConfirmation: String, termsAccepted: Boolean) {
        view?.clearErrors()

        if(validateFields(firstName, lastName, email, password, passwordConfirmation, termsAccepted)) {
            val request = SignUpRequest(firstName, lastName, email, password)

            signUp(request)
        }
    }

    private fun validateFields(firstName: String, lastName: String, email: String, password: String,
                               passwordConfirmation: String, termsAccepted: Boolean): Boolean {
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

        if(!validationHelper.checkIsNotEmpty(password)) {
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

        if(isValid && !termsAccepted) {
            view?.showTermsAndConditionsError(resourceRepository.getString(R.string.terms_and_conditions_error))
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

    private fun handleRegisterSuccess(user: UserResponse) {
        sessionManager.saveLoggedUser(user)
        openMainActivity()
    }

    private fun handleRegisterError(throwable: Throwable) {
        view?.showSnackbar(throwable.message!!)
    }

    fun onSignInClick() {
        openMainActivity()
    }

    private fun openMainActivity() {
        activitiesNavigationService.openMainActivity()
        activitiesNavigationService.finishCurrentActivity()
    }
}