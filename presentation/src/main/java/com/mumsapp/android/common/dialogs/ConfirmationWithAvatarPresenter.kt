package com.mumsapp.android.common.dialogs

import android.net.Uri
import com.mumsapp.android.base.BasePresenter
import javax.inject.Inject

class ConfirmationWithAvatarPresenter : BasePresenter<ConfirmationWithAvatarView> {

    var avatarUri: Uri? = null
    lateinit var avatarTitle: String
    lateinit var title: String
    var description: String? = null
    lateinit var confirmButtonText: String
    lateinit var cancelButtonText: String

    @Inject
    constructor()

    fun setArguments(avatarUri: Uri?, avatarTitle: String, title: String, description: String?,
                     confirmButtonText: String, cancelButtonText: String) {
        this.avatarUri = avatarUri
        this.avatarTitle = avatarTitle
        this.title = title
        this.description = description
        this.confirmButtonText = confirmButtonText
        this.cancelButtonText = cancelButtonText
    }

    override fun start() {
        if(avatarUri != null) {
            view?.showAvatar(avatarUri!!)
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