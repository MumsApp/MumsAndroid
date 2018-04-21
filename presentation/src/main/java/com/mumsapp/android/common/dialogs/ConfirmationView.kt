package com.mumsapp.android.common.dialogs

import com.mumsapp.android.base.BaseView

interface ConfirmationView : BaseView {

    fun showContent(title: String, description: String, buttonText: String)

    fun dismissView()

    fun deliverConfirmButtonClick()
}