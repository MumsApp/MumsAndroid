package com.mumsapp.android.navigation

import com.mumsapp.android.base.BaseActivity
import com.mumsapp.android.chat.ChatSettingsDialog
import com.mumsapp.android.common.dialogs.ConfirmationDialog
import com.mumsapp.android.common.dialogs.ConfirmationWithAvatarDialog
import com.mumsapp.android.product.RemoveProductDialog
import com.mumsapp.android.product.SelectImageSourceDialog
import com.mumsapp.android.profile.AccountSettingsDialog
import com.mumsapp.android.profile.AddChildDialog
import com.mumsapp.android.profile.UserDetailsSettingsDialog
import com.mumsapp.android.shop.ShopMenuDialog
import com.mumsapp.domain.model.user.UserResponse
import javax.inject.Inject

class DialogsProvider {

    private val activity: BaseActivity

    @Inject
    constructor(activity: BaseActivity) {
        this.activity = activity
    }

    fun createAccountSettingsDialog() = AccountSettingsDialog(activity)

    fun createUserDetailsSettingsDialog() = UserDetailsSettingsDialog(activity)

    fun createChatSettingsDialog() = ChatSettingsDialog(activity)

    fun createShopMenuDialog() = ShopMenuDialog(activity)

    fun createRemoveProductDialog() = RemoveProductDialog(activity)

    fun createSelectImageSourceDialog() = SelectImageSourceDialog(activity)

    fun createConfirmationWithAvatarDialog() = ConfirmationWithAvatarDialog(activity)

    fun createConfirmationDialog() = ConfirmationDialog(activity)

    fun createAddChildDialog() = AddChildDialog(activity)
}