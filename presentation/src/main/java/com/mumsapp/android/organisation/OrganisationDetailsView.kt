package com.mumsapp.android.organisation

import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.product.ImageSliderItem
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import com.mumsapp.domain.model.organisation.FeedResponse

interface OrganisationDetailsView : LifecycleView {

    fun setDetails(name: String, description: String?, avatarUrl: String?, isVerified: Boolean)

    fun showImageSlider(items: List<ImageSliderItem>)

    fun showMembers(users: List<TemplateChatRecipient>)

    fun showFeed(feed: List<FeedResponse>)
}