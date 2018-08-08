package com.mumsapp.android.common.dialogs

import com.mumsapp.android.base.BaseView

interface ConfirmationWithAvatarView : BaseView {

    fun showAvatar(avatarPath: String)

    fun setContent(avatarTitle: String, title: String, description: String?, confirmButtonText: String,
                   cancelButtonText: String)
    fun dismissView()

    fun deliverConfirmButtonClick()

    fun deliverCancelButtonClick()
}