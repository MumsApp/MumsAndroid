package com.mumsapp.android.profile

import com.mumsapp.android.base.BasePresenter
import com.mumsapp.domain.model.user.UserResponse.Child
import com.mumsapp.domain.repository.ResourceRepository
import javax.inject.Inject

class AddChildPresenter : BasePresenter<AddChildView> {

    private val resourceRepository: ResourceRepository

    var selectedSex: Int = 0
    var selectedChild: Child? = null

    @Inject
    constructor(resourceRepository: ResourceRepository) {
        this.resourceRepository = resourceRepository
    }

    fun setArguments(selectedSex: Int, selectedChild: Child?) {
        this.selectedChild = selectedChild
    }

    fun onCloseClick() {
        view?.dismissView()
    }

    fun onSaveClick(age: String, weeksChecked: Boolean, monthsChecked: Boolean, yearsChecked: Boolean) {

    }
}