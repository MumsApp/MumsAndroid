package com.mumsapp.android.common.dialogs

import android.net.Uri
import com.mumsapp.android.base.BaseView

interface ConfirmationWithAvatarView : BaseView {

    fun showAvatar(avatarUri: Uri, avatarTitle: String?)
    fun setContent(title: String, description: String, confirmButtonText: String,
                   cancelButtonText: String);
    fun dismissView()

    fun deliverConfirmButtonClick()

    fun deliverCancelButtonClick()
}