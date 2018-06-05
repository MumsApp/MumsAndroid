package com.mumsapp.android.profile

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import com.mumsapp.domain.model.mums_app_offers.TemplateMumsAppOffer

interface MyProfileView : LifecycleView {

    fun showProfileInfo(name: String, description: String)

    fun loadAvatar(url: String)

    fun showAccountSettingsDialog()

    fun showUserDetailsSettingsDialog(firstName: String?, lastName: String?, description: String?,
                                      listener: (firstName: String, lastName: String, description: String) -> Unit)

    fun showEditLocationScreen()

    fun showLocation()

    fun showNewLocation(latitude: String, longitude: String, name: String)

    fun hideLocation()

    fun showOffers(offers: List<TemplateMumsAppOffer>)

    fun showFriends(users: List<TemplateChatRecipient>)
}