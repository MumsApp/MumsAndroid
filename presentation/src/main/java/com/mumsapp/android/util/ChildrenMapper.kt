package com.mumsapp.android.util

import com.mumsapp.android.R
import com.mumsapp.domain.model.user.UserResponse.Child
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.*
import javax.inject.Inject

class ChildrenMapper {

    private val resourceRepository: ResourceRepository

    @Inject
    constructor(resourceRepository: ResourceRepository) {
        this.resourceRepository = resourceRepository
    }

    fun getReadableName(child: Child) : String {
        val sex = getReadableSex(child)
        val age = getReadableAge(child)

        return resourceRepository.getString(R.string.child_readable_pattern, sex, age)
    }

    fun getReadableSex(child: Child) : String {
        return when(child.sex) {
            SEX_MALE -> resourceRepository.getString(R.string.boy)
            SEX_FEMALE -> resourceRepository.getString(R.string.girl)
            SEX_TO_COME -> resourceRepository.getString(R.string.to_come)
            else -> throw IllegalStateException("Wrong sex value")
        }
    }

    fun getReadableAge(child: Child) : String {
        return when(child.ageUnit) {
            AGE_UNIT_WEEK -> resourceRepository.getQuantityString(R.plurals.week_plural, child.ageUnit!!, child.age!!)
            AGE_UNIT_MONTH -> resourceRepository.getQuantityString(R.plurals.month_plural, child.ageUnit!!, child.age!!)
            AGE_UNIT_YEAR -> resourceRepository.getQuantityString(R.plurals.year_plural, child.ageUnit!!, child.age!!)
            else -> throw IllegalStateException("Wrong sex value")
        }
    }
}