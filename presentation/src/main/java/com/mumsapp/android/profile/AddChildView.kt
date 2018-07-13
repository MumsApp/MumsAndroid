package com.mumsapp.android.profile

import android.graphics.drawable.Drawable
import com.mumsapp.android.base.BaseView
import com.mumsapp.domain.model.user.UserResponse.Child

interface AddChildView : BaseView {

    fun dismissView()

    fun setTitle(title: String)

    fun setSex(image: Drawable, sexName: String)

    fun setAge(age: Int)

    fun setAgeUnitCheck(weeksChecked: Boolean, monthsChecked: Boolean, yearsChecked: Boolean)

    fun deliverAction(child: Child)
}