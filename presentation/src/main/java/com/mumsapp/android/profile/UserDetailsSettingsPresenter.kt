package com.mumsapp.android.profile

import com.mumsapp.android.R
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.ValidationHelper
import javax.inject.Inject

class UserDetailsSettingsPresenter : BasePresenter<UserDetailsSettingsView> {

    private val validationHelper: ValidationHelper
    private val resourceRepository: ResourceRepository

    private var firstName: String? = null
    private var lastName: String? = null
    private var description: String? = null

    @Inject
    constructor(validationHelper: ValidationHelper, resourceRepository: ResourceRepository) {
        this.validationHelper = validationHelper
        this.resourceRepository = resourceRepository
    }

    fun setArguments(firstName: String?, lastName: String?, description: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.description = description

        view?.showInitialData(firstName, lastName, description)
    }

    fun onCloseClick() {
        view?.dismissView()
    }

    fun onSaveClick(firstName: String?, lastName: String?, description: String) {
        if(validateAndShowErrors(firstName, lastName)) {
            deliverResults(firstName!!, lastName!!, description)
            view?.dismissView()
        }
    }

    private fun validateAndShowErrors(firstName: String?, lastName: String?): Boolean {
        var isValid = true

        if(!validationHelper.checkNameValid(firstName)) {
            isValid = false
            val error = resourceRepository.getString(R.string.first_name_error)
            view?.showFirstNameError(error)
        }

        if(!validationHelper.checkNameValid(lastName)) {
            isValid = false
            val error = resourceRepository.getString(R.string.last_name_error)
            view?.showLastNameError(error)
        }

        return isValid
    }

    private fun deliverResults(firstName: String, lastName: String, description: String) {
        view?.deliverResults(firstName, lastName, description)
    }
}