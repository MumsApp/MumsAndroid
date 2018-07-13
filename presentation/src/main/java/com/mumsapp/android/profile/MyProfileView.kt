package com.mumsapp.android.profile

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import com.mumsapp.domain.model.mums_app_offers.TemplateMumsAppOffer
import com.mumsapp.domain.model.user.UserResponse

interface MyProfileView : LifecycleView {

    fun showProfileInfo(name: String, description: String?)

    fun loadAvatar(url: String)

    fun showAccountSettingsDialog()

    fun showUserDetailsSettingsDialog(firstName: String?, lastName: String?, description: String?,
                                      listener: (firstName: String, lastName: String, description: String) -> Unit)

    fun showEditLocationScreen()

    fun showLocation()

    fun showNewLocation(latitude: String, longitude: String, name: String)

    fun hideLocation()

    fun showChildren(items: List<UserResponse.Child>, editListener: (item: UserResponse.Child) -> Unit)

    fun hideChildren()

    fun showAddChildDialog(sex: Int, selectedChild: UserResponse.Child?,
                           actionListener: (child: UserResponse.Child) -> Unit)

    fun showOffers(offers: List<TemplateMumsAppOffer>)

    fun showFriends(users: List<TemplateChatRecipient>)
}