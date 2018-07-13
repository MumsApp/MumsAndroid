package com.mumsapp.android.profile

import android.graphics.drawable.Drawable
import com.mumsapp.android.R
import com.mumsapp.android.base.BasePresenter
import com.mumsapp.android.util.SEX_FEMALE
import com.mumsapp.android.util.SEX_MALE
import com.mumsapp.android.util.SEX_TO_COME
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
        this.selectedSex = selectedSex
        this.selectedChild = selectedChild
        showTitle()
        showSexDetails()
    }

    fun onCloseClick() {
        view?.dismissView()
    }

    fun onSaveClick(age: Int, weeksChecked: Boolean, monthsChecked: Boolean, yearsChecked: Boolean) {
        if (!weeksChecked && !monthsChecked && !yearsChecked) {
            val error = resourceRepository.getString(R.string.please_choose_the_type_of_age)
            view?.showToast(error)
            return
        }

        val child = constructChild(age, weeksChecked, monthsChecked, yearsChecked)
        view?.deliverAction(child)
        view?.dismissView()
    }

    private fun showTitle() {
        val titleId = if (selectedChild == null) {
            R.string.add_child
        } else {
            R.string.edit_child
        }

        val title =  resourceRepository.getString(titleId)
        view?.setTitle(title)
    }

    private fun showSexDetails() {
        val drawable: Drawable
        val sexName: String

        when (selectedSex) {
            SEX_MALE -> {
                drawable = resourceRepository.getDrawable(R.drawable.ic_male)
                sexName = resourceRepository.getString(R.string.male)
            }
            SEX_FEMALE -> {
                drawable = resourceRepository.getDrawable(R.drawable.ic_female)
                sexName = resourceRepository.getString(R.string.female)
            }
            SEX_TO_COME -> {
                drawable = resourceRepository.getDrawable(R.drawable.ic_come)
                sexName = resourceRepository.getString(R.string.to_come)
            }
            else -> throw IllegalStateException("Wrong sex")
        }

        view?.setSex(drawable, sexName)
    }

    private fun constructChild(age: Int, weeksChecked: Boolean, monthsChecked: Boolean, yearsChecked: Boolean): Child {
        val ageUnit = createAgeUnit(weeksChecked, monthsChecked, yearsChecked)

        val child: Child

        if (selectedChild == null) {
            child = Child(null, age, ageUnit, selectedSex)
        } else {
            selectedChild!!.age = age
            selectedChild!!.ageUnit = ageUnit
            child = selectedChild!!
        }

        return child
    }

    private fun createAgeUnit(weeksChecked: Boolean, monthsChecked: Boolean, yearsChecked: Boolean): Int {
        if (weeksChecked) {
            return 1
        }

        if (monthsChecked) {
            return 2
        }

        if (yearsChecked) {
            return 3
        }

        throw IllegalStateException("One checkbox must be checked")
    }
}