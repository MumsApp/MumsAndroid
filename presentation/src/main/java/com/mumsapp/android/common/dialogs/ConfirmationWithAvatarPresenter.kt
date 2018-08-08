package com.mumsapp.android.common.dialogs

import com.mumsapp.android.base.BasePresenter
import javax.inject.Inject

class ConfirmationWithAvatarPresenter : BasePresenter<ConfirmationWithAvatarView> {

    var avatarPath: String? = null
    lateinit var avatarTitle: String
    lateinit var title: String
    var description: String? = null
    lateinit var confirmButtonText: String
    lateinit var cancelButtonText: String

    @Inject
    constructor()

    fun setArguments(avatarPath: String?, avatarTitle: String, title: String, description: String?,
                     confirmButtonText: String, cancelButtonText: String) {
        this.avatarPath = avatarPath
        this.avatarTitle = avatarTitle
        this.title = title
        this.description = description
        this.confirmButtonText = confirmButtonText
        this.cancelButtonText = cancelButtonText
    }

    override fun start() {
        if(avatarPath != null) {
            view?.showAvatar(avatarPath!!)
        }

        view?.setContent(avatarTitle, title, description, confirmButtonText, cancelButtonText)
    }

    fun onCloseClick() {
        view?.deliverCancelButtonClick()
        view?.dismissView()
    }

    fun onConfirmButtonClick() {
        view?.deliverConfirmButtonClick()
        view?.dismissView()
    }

    fun onCancelButtonClick() {
        view?.deliverCancelButtonClick()
        view?.dismissView()
    }
}