package com.mumsapp.android.common.dialogs

import com.mumsapp.android.base.BasePresenter
import javax.inject.Inject

class ConfirmationPresenter : BasePresenter<ConfirmationView> {

    lateinit var title: String
    lateinit var description: String
    lateinit var confirmationButtonText: String

    @Inject
    constructor()

    fun setArguments(title: String, description: String, confirmationButtonText: String) {
        this.title = title
        this.description = description
        this.confirmationButtonText = confirmationButtonText
    }

    override fun start() {
        view?.showContent(title, description, confirmationButtonText)
    }

    fun onCloseClick() {
        view?.dismissView()
    }

    fun onConfirmButtonClick() {
        view?.deliverConfirmButtonClick()
        view?.dismissView()
    }
}