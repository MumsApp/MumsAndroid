package com.mumsapp.android.profile

import android.graphics.drawable.Drawable
import com.mumsapp.android.base.BaseView

interface AddChildView : BaseView {

    fun dismissView()

    fun setTitle(title: String)

    fun setSex(image: Drawable, sexName: String)

    fun setAge(age: String)

    fun setAgeUnitCheck(weeksChecked: Boolean, monthsChecked: Boolean, yearsChecked: Boolean)
}